package com.harshi_solution.warehouse.service;

import java.util.List;
import java.util.Map;

import com.harshi_solution.warehouse.dto.StockAllocationResponse;
import com.harshi_solution.warehouse.dto.WarehouseRequestDTO;
import com.harshi_solution.warehouse.dto.WarehouseResponseDTO;

public interface WarehouseService {

    WarehouseResponseDTO createWarehouse(WarehouseRequestDTO request);

    List<WarehouseResponseDTO> getAllWarehouses();

    WarehouseResponseDTO getWarehouseById(Long warehouseId);

    WarehouseResponseDTO updateWarehouse(Long warehouseId,
            WarehouseRequestDTO request);

    WarehouseResponseDTO updateProductQuantities(
            Long warehouseId,
            Map<Long, Integer> productQuantities);

    void deleteWarehouse(Long warehouseId);

    StockAllocationResponse allocateStock(Long productId, int quantity);

}
