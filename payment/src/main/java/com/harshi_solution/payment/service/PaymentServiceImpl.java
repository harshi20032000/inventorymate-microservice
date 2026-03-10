package com.harshi_solution.payment.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.harshi_solution.payment.dto.CreatePaymentRequest;
import com.harshi_solution.payment.dto.DocumentResponseDTO;
import com.harshi_solution.payment.dto.PaymentResponseDTO;
import com.harshi_solution.payment.entities.Document;
import com.harshi_solution.payment.entities.Payment;
import com.harshi_solution.payment.exception.PaymentNotFoundException;
import com.harshi_solution.payment.mapper.PaymentMapper;
import com.harshi_solution.payment.repo.DocumentRepository;
import com.harshi_solution.payment.repo.PaymentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final DocumentRepository documentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository, DocumentRepository documentRepository,
            PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.documentRepository = documentRepository;
    }

    @Override
    @Transactional
    public PaymentResponseDTO createPayment(CreatePaymentRequest request) {

        Payment payment = paymentMapper.toPaymentEntity(request);

        payment.setPaymentUpdatedDate(LocalDate.now());

        Payment saved = paymentRepository.save(payment);

        return paymentMapper.toPaymentDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDTO getPaymentById(Long payId) {

        Payment payment = getPaymentOrThrow(payId);

        return paymentMapper.toPaymentDTO(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponseDTO> getPaymentsByOrderId(Long orderId) {

        List<Payment> payments = paymentRepository.findByOrderId(orderId);

        return paymentMapper.toDTOList(payments);
    }

    @Override
    public List<PaymentResponseDTO> getAllPayments() {

        return paymentMapper.toDTOList(paymentRepository.findAll());
    }

    @Override
    public void deletePayment(Long payId) {

        Payment payment = getPaymentOrThrow(payId);

        paymentRepository.delete(payment);
    }

    @Transactional(readOnly = true)
    public List<DocumentResponseDTO> getDocumentsByPaymentId(Long payId) {

        Payment payment = getPaymentOrThrow(payId);

        return paymentMapper.toDocumentDTOList(payment.getDocuments());
    }

    @Transactional(readOnly = true)
    public DocumentResponseDTO getDocumentById(Long docId) {

        Document result = documentRepository.findById(docId)
                .orElseThrow(() -> new EntityNotFoundException("Document not found"));
        return paymentMapper.toDocumentDTO(result);
    }

    @Transactional
    @Override
    public DocumentResponseDTO addDocument(Long payId, MultipartFile file) throws IOException {

        // Validate file
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        if (file.getSize() > 3_000_000) { // 3MB limit example
            throw new IllegalArgumentException("File size exceeds maximum limit 3mb");
        }

        // Fetch payment
        Payment payment = getPaymentOrThrow(payId);

        // Create Document entity
        Document document = new Document();
        document.setName(file.getOriginalFilename());
        document.setContentType(file.getContentType());
        document.setSize(file.getSize());
        document.setData(file.getBytes());
        document.setDocumentUpdatedDate(LocalDateTime.now());
        document.setPayment(payment);

        // Save document
        Document savedDocument = documentRepository.save(document);

        // Map to DTO
        return paymentMapper.toDocumentDTO(savedDocument);
    }

    private Payment getPaymentOrThrow(long payId) {
        return paymentRepository.findById(payId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with pay id : " + payId));
    }

}
