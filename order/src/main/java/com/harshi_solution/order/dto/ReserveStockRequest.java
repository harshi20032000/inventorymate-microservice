package com.harshi_solution.order.dto;

import java.util.Map;

public class ReserveStockRequest {

    private Long productId;

    private Map<Long, Integer> warehouseQuantities;
    
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public Map<Long, Integer> getWarehouseQuantities() {
        return warehouseQuantities;
    }
    public void setWarehouseQuantities(Map<Long, Integer> warehouseQuantities) {
        this.warehouseQuantities = warehouseQuantities;
    }
    public ReserveStockRequest(Long productId, Map<Long, Integer> warehouseQuantities) {
        this.productId = productId;
        this.warehouseQuantities = warehouseQuantities;
    }

   
}