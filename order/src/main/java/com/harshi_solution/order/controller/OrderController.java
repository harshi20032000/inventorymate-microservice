package com.harshi_solution.order.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harshi_solution.order.dto.AddLineItemRequest;
import com.harshi_solution.order.dto.AddPaymentRequest;
import com.harshi_solution.order.dto.BaseUIResponse;
import com.harshi_solution.order.dto.CreateOrderRequest;
import com.harshi_solution.order.dto.OrderResponseDTO;
import com.harshi_solution.order.service.OrderService;
import com.harshi_solution.order.util.ResponseBuilder;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public BaseUIResponse<OrderResponseDTO> createOrder(
            @RequestBody CreateOrderRequest request) {

        OrderResponseDTO response = orderService.createOrder(request);
        return ResponseBuilder.success("Order added successfully", response);
    }

    @PostMapping("/{orderId}/line-items")
    public BaseUIResponse<OrderResponseDTO> addLineItem(
            @PathVariable Long orderId,
            @RequestBody AddLineItemRequest request) {

        OrderResponseDTO response = orderService.addLineItem(orderId, request);

        return ResponseBuilder.success("Line Item added successfully", response);
    }

    @PostMapping("/{orderId}/confirm")
    public BaseUIResponse<OrderResponseDTO> confirmOrder(
            @PathVariable Long orderId) {

        OrderResponseDTO response = orderService.confirmOrder(orderId);

        return ResponseBuilder.success("Order Confirmed", response);
    }

    @PostMapping("/{orderId}/payments")
    public BaseUIResponse<OrderResponseDTO> addPayment(
            @PathVariable Long orderId,
            @RequestBody AddPaymentRequest request) {

        OrderResponseDTO response = orderService.addPayment(orderId, request);

        return ResponseBuilder.success("Payment added successfully", response);
    }

    @GetMapping("/{orderId}")
    public BaseUIResponse<OrderResponseDTO> getOrderById(
            @PathVariable Long orderId) {

        return ResponseBuilder.success("Order fetched successfully", orderService.getOrderById(orderId));
    }

    @GetMapping
    public BaseUIResponse<List<OrderResponseDTO>> getAllOrders() {
        return ResponseBuilder.success("Order fetched successfully", orderService.getAllOrders());
    }
}
