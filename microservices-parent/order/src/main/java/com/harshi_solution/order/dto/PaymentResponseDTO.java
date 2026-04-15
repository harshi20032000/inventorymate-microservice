package com.harshi_solution.order.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.harshi_solution.order.enums.PayMode;
import com.harshi_solution.order.enums.PayType;

public class PaymentResponseDTO {

    private Long payId;

    private LocalDate paymentConfirmationDate;

    private LocalDate paymentUpdatedDate;

    private BigDecimal payAmount;

    private PayMode payMode;

    private PayType payType;

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

     public LocalDate getPaymentUpdatedDate() {
        return paymentUpdatedDate;
    }

    public void setPaymentUpdatedDate(LocalDate paymentUpdatedDate) {
        this.paymentUpdatedDate = paymentUpdatedDate;
    }
}
