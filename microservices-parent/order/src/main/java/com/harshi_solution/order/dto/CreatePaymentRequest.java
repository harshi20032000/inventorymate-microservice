package com.harshi_solution.order.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.harshi_solution.order.enums.PayMode;
import com.harshi_solution.order.enums.PayType;

import jakarta.validation.constraints.NotNull;

public class CreatePaymentRequest {

    private Long orderId;

    private BigDecimal payAmount;

    @NotNull
    private PayMode payMode;

    @NotNull
    private PayType payType;

    private LocalDate paymentConfirmationDate;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public LocalDate getPaymentConfirmationDate() {
        return paymentConfirmationDate;
    }

    public void setPaymentConfirmationDate(LocalDate paymentConfirmationDate) {
        this.paymentConfirmationDate = paymentConfirmationDate;
    }

}
