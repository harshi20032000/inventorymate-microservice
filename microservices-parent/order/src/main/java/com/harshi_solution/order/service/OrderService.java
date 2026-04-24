package com.harshi_solution.order.service;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.harshi_solution.order.dto.AddLineItemRequest;
import com.harshi_solution.order.dto.CreateOrderRequest;
import com.harshi_solution.order.dto.CreatePaymentRequest;
import com.harshi_solution.order.dto.OrderResponseDTO;
@Service
public interface OrderService {

    OrderResponseDTO createOrder(CreateOrderRequest request);

    OrderResponseDTO addLineItem(@NonNull Long orderId, AddLineItemRequest request);

    OrderResponseDTO confirmOrder(@NonNull Long orderId);

    OrderResponseDTO addPayment(@NonNull Long orderId, CreatePaymentRequest request);

    OrderResponseDTO getOrderById(@NonNull Long orderId);

    List<OrderResponseDTO> getAllOrders();
}
