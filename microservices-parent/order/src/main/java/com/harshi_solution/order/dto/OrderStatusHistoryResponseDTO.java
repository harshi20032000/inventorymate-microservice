package com.harshi_solution.order.dto;

import java.time.LocalDateTime;

import com.harshi_solution.order.util.OrderStatus;

public class OrderStatusHistoryResponseDTO {

    private OrderStatus orderStatus;
    private LocalDateTime updateTimestamp;

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

}