package com.harshi_solution.order.entities;

import java.time.LocalDateTime;

import com.harshi_solution.order.util.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime updateTimestamp;

    public OrderStatusHistory(OrderStatus orderStatus2, LocalDateTime updateTimestamp2) {
        this.orderStatus = orderStatus2;
        this.updateTimestamp = updateTimestamp2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(LocalDateTime updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    @Override
    public String toString() {
        return "OrderStatusHistory [id=" + id + ", order=" + order + ", orderStatus=" + orderStatus
                + ", updateTimestamp=" + updateTimestamp + "]";
    }

    public OrderStatusHistory(Long id, Order order, OrderStatus orderStatus, LocalDateTime updateTimestamp) {
        super();
        this.id = id;
        this.order = order;
        this.orderStatus = orderStatus;
        this.updateTimestamp = updateTimestamp;
    }

    public OrderStatusHistory() {
        super();
    }

}
