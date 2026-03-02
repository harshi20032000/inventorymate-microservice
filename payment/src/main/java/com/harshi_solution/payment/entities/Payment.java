package com.harshi_solution.payment.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.harshi_solution.payment.enums.PayMode;
import com.harshi_solution.payment.enums.PayType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payId;

    private LocalDate paymentConfirmationDate;

    private LocalDate paymentUpdatedDate;

    private BigDecimal payAmount;

    @Enumerated(EnumType.STRING)
    private PayMode payMode; // CASH, UPI, BANK_TRANSFER

    @Enumerated(EnumType.STRING)
    private PayType payType; // ADVANCE, PARTIAL, FULL

    private Long orderId; // Only ID reference

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents = new ArrayList<>();

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

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
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