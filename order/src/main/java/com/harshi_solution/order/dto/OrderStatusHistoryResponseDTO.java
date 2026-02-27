package com.harshi_solution.order.dto;

import java.time.LocalDateTime;

public class OrderStatusHistoryResponseDTO {

    private String orderStatus;
    private LocalDateTime updateTimestamp;
    public String getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    public LocalDateTime getUpdateTimestamp() {
        return updateTimestamp;
    }
    public void setUpdateTimestamp(LocalDateTime updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    // getters & setters
    
}