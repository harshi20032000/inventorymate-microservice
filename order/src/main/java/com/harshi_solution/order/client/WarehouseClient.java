package com.harshi_solution.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.harshi_solution.order.dto.BaseUIResponse;
import com.harshi_solution.order.dto.StockAllocationResponse;
import com.harshi_solution.order.dto.WarehouseResponseDTO;

@FeignClient(name = "warehouse-service", url = "${warehouse.service.url}")
public interface WarehouseClient {

    @GetMapping("/api/v1/warehouses/{id}")
    BaseUIResponse<WarehouseResponseDTO> getWarehouseById(@PathVariable Long id);

    @PostMapping("/api/v1/warehouses/allocate")
    BaseUIResponse<StockAllocationResponse> allocateStock(
            @RequestParam Long productId,
            @RequestParam int quantity);
}
