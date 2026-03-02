package com.harshi_solution.payment.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.harshi_solution.payment.dto.CreatePaymentRequest;
import com.harshi_solution.payment.dto.DocumentRequestDTO;
import com.harshi_solution.payment.dto.DocumentResponseDTO;
import com.harshi_solution.payment.dto.PaymentResponseDTO;
import com.harshi_solution.payment.entities.Document;
import com.harshi_solution.payment.entities.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "payId", ignore = true)
     @Mapping(target = "paymentUpdatedDate", ignore = true)
     @Mapping(target = "documents", ignore = true)
    Payment toPaymentEntity(CreatePaymentRequest request);

    PaymentResponseDTO toPaymentDTO(Payment payment);

    List<PaymentResponseDTO> toDTOList(List<Payment> payments);

    List<DocumentResponseDTO> toDocumentDTOList(List<Document> documents);

    // @Mapping(target = "documentUpdatedDate", ignore = true)
    // @Mapping(target = "payment", ignore = true)
    // @Mapping(target = "docId", ignore = true)
    // Document toDocument(DocumentRequestDTO request);

    DocumentResponseDTO toDocumentDTO(Document document);
}
