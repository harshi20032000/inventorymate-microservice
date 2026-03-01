package com.harshi_solution.warehouse.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harshi_solution.warehouse.dto.BaseUIResponse;
import com.harshi_solution.warehouse.dto.StockAllocationResponse;
import com.harshi_solution.warehouse.dto.WarehouseRequestDTO;
import com.harshi_solution.warehouse.dto.WarehouseResponseDTO;
import com.harshi_solution.warehouse.service.WarehouseService;
import com.harshi_solution.warehouse.util.ResponseBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    public BaseUIResponse<WarehouseResponseDTO> createWarehouse(
            @Valid @RequestBody WarehouseRequestDTO request) {

        WarehouseResponseDTO created = warehouseService.createWarehouse(request);

        return ResponseBuilder.success("Warehouse created successfully", created);
    }

    @GetMapping
    public BaseUIResponse<List<WarehouseResponseDTO>> getAllWarehouses() {

        List<WarehouseResponseDTO> warehouses = warehouseService.getAllWarehouses();

        return ResponseBuilder.success("Warehouse fetched successfully", warehouses);
    }

    @GetMapping("/{id}")
    public BaseUIResponse<WarehouseResponseDTO> getWarehouseById(
            @PathVariable Long id) {

        WarehouseResponseDTO response = warehouseService.getWarehouseById(id);

        return ResponseBuilder.success("Warehouse fetched successfully", response);
    }

    @PutMapping("/{id}")
    public BaseUIResponse<WarehouseResponseDTO> updateWarehouse(
            @PathVariable Long id,
            @Valid @RequestBody WarehouseRequestDTO request) {

        WarehouseResponseDTO updated = warehouseService.updateWarehouse(id, request);

        return ResponseBuilder.success("Warehouse updated successfully", updated);
    }

    @PatchMapping("/{id}/products")
    public BaseUIResponse<WarehouseResponseDTO> updateProductQuantities(
            @PathVariable Long id,
            @RequestBody Map<Long, Integer> productQuantities) {

        WarehouseResponseDTO updated = warehouseService.updateProductQuantities(id, productQuantities);

        return ResponseBuilder.success(
                "Product quantities updated successfully",
                updated);
    }

    @DeleteMapping("/{id}")
    public BaseUIResponse<Void> deleteWarehouse(
            @PathVariable Long id) {

        warehouseService.deleteWarehouse(id);

        return ResponseBuilder.success("Warehouse deleted successfully", null);
    }

    @PostMapping("/allocate")
    BaseUIResponse<StockAllocationResponse> allocateStock(
            @RequestParam Long productId,
            @RequestParam int quantity){
                return ResponseBuilder.success("Allocate Stock result", warehouseService.allocateStock(productId, quantity));
            }
}
