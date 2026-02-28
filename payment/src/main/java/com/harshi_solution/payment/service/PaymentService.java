package com.harshi_solution.payment.service;

import java.util.List;

import com.harshi_solution.payment.dto.CreatePaymentRequest;
import com.harshi_solution.payment.dto.PaymentResponseDTO;

public interface PaymentService {

    PaymentResponseDTO createPayment(CreatePaymentRequest request);

    PaymentResponseDTO getPaymentById(Long payId);

    List<PaymentResponseDTO> getPaymentsByOrderId(Long orderId);

    List<PaymentResponseDTO> getAllPayments();

    void deletePayment(Long payId);
}
