package com.harshi_solution.payment.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harshi_solution.payment.dto.CreatePaymentRequest;
import com.harshi_solution.payment.dto.PaymentResponseDTO;
import com.harshi_solution.payment.entities.Payment;
import com.harshi_solution.payment.mapper.PaymentMapper;
import com.harshi_solution.payment.repo.PaymentRepository;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public PaymentResponseDTO createPayment(CreatePaymentRequest request) {

        Payment payment = paymentMapper.toEntity(request);

        payment.setPayDate(LocalDate.now());

        Payment saved = paymentRepository.save(payment);

        return paymentMapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDTO getPaymentById(Long payId) {

        Payment payment = paymentRepository.findById(payId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        return paymentMapper.toDTO(payment);
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

        Payment payment = paymentRepository.findById(payId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        paymentRepository.delete(payment);
    }

}
