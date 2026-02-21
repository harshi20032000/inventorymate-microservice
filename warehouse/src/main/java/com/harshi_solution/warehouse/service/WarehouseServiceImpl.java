package com.harshi_solution.warehouse.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harshi_solution.warehouse.dto.WarehouseRequestDTO;
import com.harshi_solution.warehouse.dto.WarehouseResponseDTO;
import com.harshi_solution.warehouse.entities.Warehouse;
import com.harshi_solution.warehouse.repo.WarehouseRepo;

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
}
