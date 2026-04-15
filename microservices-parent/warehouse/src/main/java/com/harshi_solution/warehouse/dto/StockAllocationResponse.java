package com.harshi_solution.warehouse.dto;

import java.util.Map;

public class StockAllocationResponse {

    private boolean sufficient;

    private int requestedQuantity;

    private int totalAvailable;

    private Map<Long, Integer> warehouseAllocations;

    

    public StockAllocationResponse(boolean sufficient, int requestedQuantity, int totalAvailable,
            Map<Long, Integer> warehouseAllocations) {
        this.sufficient = sufficient;
        this.requestedQuantity = requestedQuantity;
        this.totalAvailable = totalAvailable;
        this.warehouseAllocations = warehouseAllocations;
    }

    public boolean isSufficient() {
        return sufficient;
    }

    public void setSufficient(boolean sufficient) {
        this.sufficient = sufficient;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(int requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public int getTotalAvailable() {
        return totalAvailable;
    }

    public void setTotalAvailable(int totalAvailable) {
        this.totalAvailable = totalAvailable;
    }

    public Map<Long, Integer> getWarehouseAllocations() {
        return warehouseAllocations;
    }

    public void setWarehouseAllocations(Map<Long, Integer> warehouseAllocations) {
        this.warehouseAllocations = warehouseAllocations;
    }
    
}
