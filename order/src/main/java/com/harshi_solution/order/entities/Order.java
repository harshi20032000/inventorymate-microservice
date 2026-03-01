package com.harshi_solution.order.entities;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.harshi_solution.order.util.OrderStatus;

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
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Instant orderDate;

    private Long repId;

    private Long partyId;
    
    private Long transportId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLineItem> orderLineItems = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderStatusHistory> statusHistory = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus currentStatus;

    private Integer totalOrderQuantity;

    private BigDecimal totalBillAmount;

    private BigDecimal remainingBillAmount;

    // Constructors, getters, and setters

    public Order() {
        // Default constructor
    }

    public Order(Instant orderDate) {
        this.orderDate = orderDate;
    }

    // Getters and setters

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

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems(List<OrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public void addLineItem(OrderLineItem lineItem) {
        // Set the order for the line item
        lineItem.setOrder(this);

        // Add the line item to the list of line items
        orderLineItems.add(lineItem);
    }

    public void removeLineItem(OrderLineItem lineItem) {
        // Remove the line item from the list of line items
        orderLineItems.remove(lineItem);

        // Set the order for the line item to null
        if (lineItem != null) {
            lineItem.setOrder(null);
        }
    }

    public List<OrderStatusHistory> getStatusHistory() {
        return statusHistory;
    }

    public void setStatusHistory(List<OrderStatusHistory> statusHistory) {
        this.statusHistory = statusHistory;
    }

    public void addStatusChange(OrderStatusHistory change) {
        statusHistory.add(change);
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

    public Integer getTotalOrderQuantity() {
        return totalOrderQuantity;
    }

    public void setTotalOrderQuantity(Integer totalOrderQuantity) {
        this.totalOrderQuantity = totalOrderQuantity;
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

    public OrderStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(OrderStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

}
