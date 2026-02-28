package com.harshi_solution.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.harshi_solution.order.dto.WarehouseResponseDTO;

@FeignClient(name = "warehouse-service", url = "${warehouse.service.url}")
public interface WarehouseClient {

    @GetMapping("/api/v1/warehouses/{id}")
    WarehouseResponseDTO getWarehouseById(@PathVariable Long id);
}
