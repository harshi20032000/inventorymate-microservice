package com.harshi_solution.order.dto;


import java.math.BigDecimal;
import java.util.Map;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AddLineItemRequest {

    @NotNull
    private Long productId;

    @Positive
    private int quantity;

    @NotNull
    @Positive
    private BigDecimal rate;

    // Optional: warehouse distribution
    private Map<Long, Integer> warehouseQuantities;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Map<Long, Integer> getWarehouseQuantities() {
        return warehouseQuantities;
    }

    public void setWarehouseQuantities(Map<Long, Integer> warehouseQuantities) {
        this.warehouseQuantities = warehouseQuantities;
    }

    // getters & setters
    
}