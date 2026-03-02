package com.harshi_solution.payment.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.harshi_solution.payment.dto.CreatePaymentRequest;
import com.harshi_solution.payment.dto.DocumentResponseDTO;
import com.harshi_solution.payment.dto.PaymentResponseDTO;

public interface PaymentService {

    PaymentResponseDTO createPayment(CreatePaymentRequest request);

    PaymentResponseDTO getPaymentById(Long payId);

    List<PaymentResponseDTO> getPaymentsByOrderId(Long orderId);

    List<PaymentResponseDTO> getAllPayments();

    void deletePayment(Long payId);

    List<DocumentResponseDTO> getDocumentsByPaymentId(Long id);

    DocumentResponseDTO getDocumentById(Long docId);

    DocumentResponseDTO addDocument(Long paymentId, MultipartFile file) throws IOException;


}
