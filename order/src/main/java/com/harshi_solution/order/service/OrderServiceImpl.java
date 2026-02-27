package com.harshi_solution.order.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harshi_solution.order.dto.AddLineItemRequest;
import com.harshi_solution.order.dto.AddPaymentRequest;
import com.harshi_solution.order.dto.CreateOrderRequest;
import com.harshi_solution.order.dto.OrderResponseDTO;
import com.harshi_solution.order.entities.Order;
import com.harshi_solution.order.entities.OrderStatusHistory;
import com.harshi_solution.order.mapper.OrderMapper;
import com.harshi_solution.order.repo.OrderRepository;
import com.harshi_solution.order.util.OrderStatus;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    // private final ProductClient productClient;
    // private final WarehouseClient warehouseClient;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
public OrderResponseDTO createOrder(CreateOrderRequest request) {

    Order order = orderMapper.toEntity(request);

    //Business defaults
    order.setOrderDate(Instant.now());
    order.setTotalOrderQuantity(0);
    order.setTotalBillAmount(BigDecimal.ZERO);
    order.setRemainingBillAmount(BigDecimal.ZERO);

    //Initial Status
    OrderStatusHistory history = new OrderStatusHistory();
    history.setOrder(order);
    history.setOrderStatus(OrderStatus.CREATED);
    history.setUpdateTimestamp(LocalDateTime.now());

    order.getStatusHistory().add(history);

    //Save
    Order savedOrder = orderRepository.save(order);

    //Map entity → response
    return orderMapper.toDto(savedOrder);
}

    @Override
    public OrderResponseDTO addLineItem(Long orderId, AddLineItemRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addLineItem'");
    }

    @Override
    public OrderResponseDTO confirmOrder(Long orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'confirmOrder'");
    }

    @Override
    public OrderResponseDTO addPayment(Long orderId, AddPaymentRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addPayment'");
    }

    @Override
    public OrderResponseDTO getOrderById(Long orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrderById'");
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllOrders'");
    }
}
