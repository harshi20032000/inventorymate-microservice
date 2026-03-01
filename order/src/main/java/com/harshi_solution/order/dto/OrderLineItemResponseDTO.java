package com.harshi_solution.order.dto;

import java.math.BigDecimal;
import java.util.Map;

public class OrderLineItemResponseDTO {

    private Long id;
    private Long productId;
    private int quantity;
    private BigDecimal rate;
    private Map<Long, Integer> warehouseQuantities;

    public Map<Long, Integer> getWarehouseQuantities() {
        return warehouseQuantities;
    }

    public void setWarehouseQuantities(Map<Long, Integer> warehouseQuantities) {
        this.warehouseQuantities = warehouseQuantities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

}
