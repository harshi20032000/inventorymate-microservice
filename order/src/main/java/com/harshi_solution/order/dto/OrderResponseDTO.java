package com.harshi_solution.order.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class OrderResponseDTO {

    private Long orderId;
    private Instant orderDate;

    private Long repId;
    private Long partyId;
    private Long transportId;

    private Integer totalOrderQuantity;
    private BigDecimal totalBillAmount;
    private BigDecimal remainingBillAmount;

    private String currentStatus;

    private List<OrderLineItemResponseDTO> orderLineItems;
    private List<OrderStatusHistoryResponseDTO> statusHistory;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public Long getRepId() {
        return repId;
    }

    public void setRepId(Long repId) {
        this.repId = repId;
    }

    public Long getPartyId() {
        return partyId;
    }

    public void setPartyId(Long partyId) {
        this.partyId = partyId;
    }

    public Long getTransportId() {
        return transportId;
    }

    public void setTransportId(Long transportId) {
        this.transportId = transportId;
    }

    public Integer getTotalOrderQuantity() {
        return totalOrderQuantity;
    }

    public void setTotalOrderQuantity(Integer totalOrderQuantity) {
        this.totalOrderQuantity = totalOrderQuantity;
    }

    public BigDecimal getTotalBillAmount() {
        return totalBillAmount;
    }

    public void setTotalBillAmount(BigDecimal totalBillAmount) {
        this.totalBillAmount = totalBillAmount;
    }

    public BigDecimal getRemainingBillAmount() {
        return remainingBillAmount;
    }

    public void setRemainingBillAmount(BigDecimal remainingBillAmount) {
        this.remainingBillAmount = remainingBillAmount;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public List<OrderLineItemResponseDTO> getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems(List<OrderLineItemResponseDTO> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public List<OrderStatusHistoryResponseDTO> getStatusHistory() {
        return statusHistory;
    }

    public void setStatusHistory(List<OrderStatusHistoryResponseDTO> statusHistory) {
        this.statusHistory = statusHistory;
    }

    // getters & setters

}
