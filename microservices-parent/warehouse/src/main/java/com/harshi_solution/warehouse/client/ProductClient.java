package com.harshi_solution.warehouse.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.harshi_solution.warehouse.config.FeignConfig;

@FeignClient(name = "product-service", configuration = FeignConfig.class)
public interface ProductClient {

   @GetMapping("/products/{id}/name")
    String getProductName(@PathVariable Long id);
}
