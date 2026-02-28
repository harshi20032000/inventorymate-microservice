package com.harshi_solution.payment.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.harshi_solution.payment.dto.CreatePaymentRequest;
import com.harshi_solution.payment.dto.PaymentResponseDTO;
import com.harshi_solution.payment.entities.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    Payment toEntity(CreatePaymentRequest request);

    @Mapping(source = "document.docId", target = "documentId")
    PaymentResponseDTO toDTO(Payment payment);

    List<PaymentResponseDTO> toDTOList(List<Payment> payments);
}
