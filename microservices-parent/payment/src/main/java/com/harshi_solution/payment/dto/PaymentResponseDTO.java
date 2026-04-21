package com.harshi_solution.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.harshi_solution.payment.enums.PayMode;
import com.harshi_solution.payment.enums.PayType;

public class PaymentResponseDTO {

    private Long payId;

    private LocalDate paymentConfirmationDate;

    private LocalDate paymentUpdatedDate;

    private BigDecimal payAmount;

    private PayMode payMode;

    private PayType payType;

    private Long orderId;

    private List<DocumentRequestDTO> documents;

    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public PayMode getPayMode() {
        return payMode;
    }

    public void setPayMode(PayMode payMode) {
        this.payMode = payMode;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDate getPaymentConfirmationDate() {
        return paymentConfirmationDate;
    }

    public void setPaymentConfirmationDate(LocalDate paymentConfirmationDate) {
        this.paymentConfirmationDate = paymentConfirmationDate;
    }

    public List<DocumentRequestDTO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentRequestDTO> documents) {
        this.documents = documents;
    }

     public LocalDate getPaymentUpdatedDate() {
        return paymentUpdatedDate;
    }

    public void setPaymentUpdatedDate(LocalDate paymentUpdatedDate) {
        this.paymentUpdatedDate = paymentUpdatedDate;
    }
}
