package com.harshi_solution.warehouse.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harshi_solution.warehouse.dto.ReserveStockRequest;
import com.harshi_solution.warehouse.dto.StockAllocationResponse;
import com.harshi_solution.warehouse.dto.WarehouseRequestDTO;
import com.harshi_solution.warehouse.dto.WarehouseResponseDTO;
import com.harshi_solution.warehouse.entities.Warehouse;
import com.harshi_solution.warehouse.exception.InsufficientStockException;
import com.harshi_solution.warehouse.repo.WarehouseRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepo warehouseRepository;

    public WarehouseServiceImpl(WarehouseRepo warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public WarehouseResponseDTO createWarehouse(WarehouseRequestDTO request) {

        Warehouse warehouse = new Warehouse();
        warehouse.setWareName(request.getWareName().toUpperCase());
        warehouse.setWareCode(request.getWareCode().toUpperCase());

        Warehouse saved = warehouseRepository.save(warehouse);

        return mapToResponse(saved);
    }

    @Override
    public List<WarehouseResponseDTO> getAllWarehouses() {

        return warehouseRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public WarehouseResponseDTO getWarehouseById(Long warehouseId) {

        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        return mapToResponse(warehouse);
    }

    @Override
    public WarehouseResponseDTO updateWarehouse(
            Long warehouseId,
            WarehouseRequestDTO request) {

        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        warehouse.setWareName(request.getWareName().toUpperCase());
        warehouse.setWareCode(request.getWareCode().toUpperCase());

        return mapToResponse(warehouse);
    }

    @Override
    public WarehouseResponseDTO updateProductQuantities(
            Long warehouseId,
            Map<Long, Integer> productQuantities) {

        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        warehouse.setProductQuantities(productQuantities);

        return mapToResponse(warehouse);
    }

    @Override
    public void deleteWarehouse(Long warehouseId) {
        warehouseRepository.deleteById(warehouseId);
    }

    private WarehouseResponseDTO mapToResponse(Warehouse warehouse) {

        WarehouseResponseDTO dto = new WarehouseResponseDTO();
        dto.setWareId(warehouse.getWareId());
        dto.setWareName(warehouse.getWareName());
        dto.setWareCode(warehouse.getWareCode());
        dto.setProductQuantities(warehouse.getProductQuantities());

        return dto;
    }

    @Override
    public StockAllocationResponse allocateStock(Long productId, int quantity) {

        List<Warehouse> warehouses = warehouseRepository.findAll();

        int totalAvailable = warehouses.stream()
                .map(w -> w.getProductQuantities().getOrDefault(productId, 0))
                .mapToInt(Integer::intValue)
                .sum();

        if (totalAvailable < quantity) {
            throw new InsufficientStockException(
                    "Requested: " + quantity + ", Available: " + totalAvailable);
        }

        // Now do allocation
        int remaining = quantity;
        Map<Long, Integer> allocation = new LinkedHashMap<>();

        for (Warehouse warehouse : warehouses) {

            int available = warehouse.getProductQuantities()
                    .getOrDefault(productId, 0);

            if (available <= 0)
                continue;

            int allocated = Math.min(available, remaining);

            allocation.put(warehouse.getWareId(), allocated);

            remaining -= allocated;

            if (remaining == 0)
                break;
        }

        return new StockAllocationResponse(
                true,
                quantity,
                totalAvailable,
                allocation);
    }

    @Override
    @Transactional
    public void reserveStock(ReserveStockRequest request) {

        Long productId = request.getProductId();
        Map<Long, Integer> allocation = request.getWarehouseQuantities();

        for (Map.Entry<Long, Integer> entry : allocation.entrySet()) {

            Long warehouseId = entry.getKey();
            Integer quantityToDeduct = entry.getValue();

            Warehouse warehouse = warehouseRepository.findById(warehouseId)
                    .orElseThrow(() -> new EntityNotFoundException("Warehouse not found with id - " + warehouseId));

            Integer available = warehouse.getProductQuantities()
                    .getOrDefault(productId, 0);

            if (available < quantityToDeduct) {
                throw new InsufficientStockException(
                        "Stock changed. Available: " + available);
            }

            warehouse.getProductQuantities()
                    .put(productId, available - quantityToDeduct);

            warehouseRepository.save(warehouse);
        }
    }
}
