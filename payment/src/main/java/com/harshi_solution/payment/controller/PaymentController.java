package com.harshi_solution.payment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harshi_solution.payment.dto.BaseUIResponse;
import com.harshi_solution.payment.dto.CreatePaymentRequest;
import com.harshi_solution.payment.dto.PaymentResponseDTO;
import com.harshi_solution.payment.service.PaymentService;
import com.harshi_solution.payment.util.ResponseBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public BaseUIResponse<PaymentResponseDTO> createPayment(
            @Valid @RequestBody CreatePaymentRequest request) {

        PaymentResponseDTO created = paymentService.createPayment(request);

        return ResponseBuilder.success("Payment created successfully", created);
    }

    @GetMapping("/{payId}")
    public BaseUIResponse<PaymentResponseDTO> getPaymentById(
            @PathVariable Long payId) {

        PaymentResponseDTO response = paymentService.getPaymentById(payId);

        return ResponseBuilder.success("Payment fetched successfully", response);
    }

    @GetMapping("/order/{orderId}")
    public BaseUIResponse<List<PaymentResponseDTO>> getPaymentsByOrder(
            @PathVariable Long orderId) {

        List<PaymentResponseDTO> response = paymentService.getPaymentsByOrderId(orderId);

        return ResponseBuilder.success("Payments fetched successfully", response);
    }

    @GetMapping
    public BaseUIResponse<List<PaymentResponseDTO>> getAllPayments() {

        List<PaymentResponseDTO> response = paymentService.getAllPayments();
        return ResponseBuilder.success("Payments fetched successfully", response);
    }

    @DeleteMapping("/{payId}")
    public BaseUIResponse<Void> deletePayment(@PathVariable Long payId) {

        paymentService.deletePayment(payId);

        return ResponseBuilder.success("Payment deleted successfully", null);
    }
}
