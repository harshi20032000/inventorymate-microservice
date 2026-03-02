package com.harshi_solution.order.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentResponseDTO {

    private Long payId;

    private LocalDate paymentConfirmationDate;

    private LocalDate paymentUpdatedDate;

    private BigDecimal payAmount;

    private String payMode;

    private String payType;

    private Long orderId;

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

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
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

     public LocalDate getPaymentUpdatedDate() {
        return paymentUpdatedDate;
    }

    public void setPaymentUpdatedDate(LocalDate paymentUpdatedDate) {
        this.paymentUpdatedDate = paymentUpdatedDate;
    }
}
