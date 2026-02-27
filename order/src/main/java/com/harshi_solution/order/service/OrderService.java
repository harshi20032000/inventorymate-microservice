package com.harshi_solution.order.service;

import java.util.List;

import com.harshi_solution.order.dto.AddLineItemRequest;
import com.harshi_solution.order.dto.AddPaymentRequest;
import com.harshi_solution.order.dto.CreateOrderRequest;
import com.harshi_solution.order.dto.OrderResponseDTO;

public interface OrderService {

    OrderResponseDTO createOrder(CreateOrderRequest request);

    OrderResponseDTO addLineItem(Long orderId, AddLineItemRequest request);

    OrderResponseDTO confirmOrder(Long orderId);

    OrderResponseDTO addPayment(Long orderId, AddPaymentRequest request);

    OrderResponseDTO getOrderById(Long orderId);

    List<OrderResponseDTO> getAllOrders();
}
