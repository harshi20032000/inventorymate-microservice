package com.harshi_solution.order.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harshi_solution.order.client.PaymentClient;
import com.harshi_solution.order.client.ProductClient;
import com.harshi_solution.order.client.WarehouseClient;
import com.harshi_solution.order.dto.AddLineItemRequest;
import com.harshi_solution.order.dto.BaseUIResponse;
import com.harshi_solution.order.dto.CreateOrderRequest;
import com.harshi_solution.order.dto.CreatePaymentRequest;
import com.harshi_solution.order.dto.OrderResponseDTO;
import com.harshi_solution.order.dto.PaymentResponseDTO;
import com.harshi_solution.order.dto.ProductResponseDTO;
import com.harshi_solution.order.dto.ReserveStockRequest;
import com.harshi_solution.order.dto.StockAllocationResponse;
import com.harshi_solution.order.entities.Order;
import com.harshi_solution.order.entities.OrderLineItem;
import com.harshi_solution.order.entities.OrderStatusHistory;
import com.harshi_solution.order.exception.customExceptionHandler.OrderNotFoundException;
import com.harshi_solution.order.mapper.OrderMapper;
import com.harshi_solution.order.repo.OrderRepository;
import com.harshi_solution.order.util.OrderStatus;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductClient productClient;
    private final WarehouseClient warehouseClient;
    private final PaymentClient paymentClient;

    public OrderServiceImpl(OrderRepository orderRepository, ProductClient productClient,
            WarehouseClient warehouseClient, PaymentClient paymentClient,
            OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
        this.warehouseClient = warehouseClient;
        this.paymentClient = paymentClient;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderResponseDTO createOrder(CreateOrderRequest request) {

        Order order = orderMapper.toEntity(request);

        // Business defaults
        order.setOrderDate(Instant.now());
        order.setTotalOrderQuantity(0);
        order.setTotalBillAmount(BigDecimal.ZERO);
        order.setRemainingBillAmount(BigDecimal.ZERO);
        order.setCurrentStatus(OrderStatus.CREATED);

        // Initial Status
        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrder(order);
        history.setOrderStatus(OrderStatus.CREATED);
        history.setUpdateTimestamp(LocalDateTime.now());

        order.getStatusHistory().add(history);

        // Save
        Order savedOrder = orderRepository.save(order);

        // Map entity → response
        return orderMapper.toDto(savedOrder);
    }

    @Override
    public OrderResponseDTO addLineItem(Long orderId, AddLineItemRequest request) {

        // Fetch Order
        Order order = getOrderOrThrow(orderId);

        // Fetch Product via Feign (NOT repository)
        Long productId = request.getProductId();
        int quantity = request.getQuantity();
        BigDecimal rate = request.getRate();
        ProductResponseDTO product = productClient.getProductById(productId).getResponsePayload();

        if (product == null) {
            throw new RuntimeException("Product not found with id: " + productId);
        }
        if (product.getBasePrice().compareTo(rate) > 0) {
            throw new RuntimeException("Product with id: " + productId + "has rate lower than purchase price.");
        }

        StockAllocationResponse allocation = warehouseClient.allocateStock(productId, quantity).getResponsePayload();
        if (!allocation.isSufficient()) {
            throw new RuntimeException("Items not in Stock!");
        }

        // Create Line Item
        OrderLineItem lineItem = new OrderLineItem();
        lineItem.setOrder(order);
        lineItem.setProductId(productId);
        lineItem.setQuantity(quantity);
        lineItem.setRate(rate);
        lineItem.setWarehouseQuantities(allocation.getWarehouseAllocations());

        // Attach to Order
        order.getOrderLineItems().add(lineItem);

        // Update Totals
        int newTotalQty = order.getTotalOrderQuantity() + request.getQuantity();
        order.setTotalOrderQuantity(newTotalQty);

        BigDecimal lineTotal = rate.multiply(BigDecimal.valueOf(request.getQuantity()));
        BigDecimal newBillAmount = order.getTotalBillAmount().add(lineTotal);

        order.setTotalBillAmount(newBillAmount);
        order.setRemainingBillAmount(newBillAmount);

        // Save
        Order savedOrder = orderRepository.save(order);

        return orderMapper.toDto(savedOrder);
    }

    @Override
    public OrderResponseDTO confirmOrder(Long orderId) {

        // Fetch order
        Order order = getOrderOrThrow(orderId);

        // Validate current status
        if (order.getCurrentStatus() != OrderStatus.CREATED) {
            throw new RuntimeException("Only CREATED orders can be confirmed");
        }

        // Validate line items exist
        if (order.getOrderLineItems().isEmpty()) {
            throw new RuntimeException("Cannot confirm order without line items");
        }

        // Reserve stock via Warehouse Service
        // Later use reserveStockBatch Function send entire order aloocation in one go.
        for (OrderLineItem item : order.getOrderLineItems()) {

            warehouseClient.reserveStock(new ReserveStockRequest(item.getProductId(),
                    item.getWarehouseQuantities()));
        }

        // Update current status
        order.setCurrentStatus(OrderStatus.CONFIRMED);

        // Add status history entry
        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrder(order);
        history.setOrderStatus(OrderStatus.CONFIRMED);
        history.setUpdateTimestamp(LocalDateTime.now());

        order.getStatusHistory().add(history);

        // Save
        Order savedOrder = orderRepository.save(order);

        return orderMapper.toDto(savedOrder);
    }

    @Override
    public OrderResponseDTO addPayment(Long orderId, CreatePaymentRequest request) {

        Order order = getOrderOrThrow(orderId);

        if (order.getCurrentStatus() != OrderStatus.CONFIRMED &&
                order.getCurrentStatus() != OrderStatus.PARTIALLY_PAID) {
            throw new RuntimeException("Payment not allowed for current order status");
        }

        BigDecimal paymentAmount = request.getPayAmount();

        if (paymentAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Payment amount must be greater than zero");
        }

        if (paymentAmount.compareTo(order.getRemainingBillAmount()) > 0) {
            throw new RuntimeException("Payment exceeds remaining bill amount");
        }

        // 🔹 STEP 1: Call Payment Microservice FIRST
        CreatePaymentRequest paymentRequest = new CreatePaymentRequest();
        paymentRequest.setOrderId(orderId);
        paymentRequest.setPayAmount(paymentAmount);
        paymentRequest.setPayMode(request.getPayMode());
        paymentRequest.setPayType(request.getPayType());
        paymentRequest.setPaymentConfirmationDate(request.getPaymentConfirmationDate());

        BaseUIResponse<PaymentResponseDTO> paymentResponse = paymentClient.createPayment(paymentRequest);

        if (paymentResponse == null || paymentResponse.getResponsePayload() == null) {
            throw new RuntimeException("Payment service failed");
        }

        // 🔹 STEP 2: Only after success, update order

        BigDecimal newRemaining = order.getRemainingBillAmount().subtract(paymentAmount);
        order.setRemainingBillAmount(newRemaining);

        OrderStatus newStatus = newRemaining.compareTo(BigDecimal.ZERO) == 0
                ? OrderStatus.FULLY_PAID
                : OrderStatus.PARTIALLY_PAID;

        order.setCurrentStatus(newStatus);

        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrder(order);
        history.setOrderStatus(newStatus);
        history.setUpdateTimestamp(LocalDateTime.now());

        order.getStatusHistory().add(history);

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toDto(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO getOrderById(Long orderId) {

        Order order = getOrderOrThrow(orderId);

        return orderMapper.toDto(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getAllOrders() {

        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    private Order getOrderOrThrow(long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with order id: " + orderId));
    }
}
