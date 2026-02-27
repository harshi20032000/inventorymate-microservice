package com.harshi_solution.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.harshi_solution.order.dto.ProductResponseDTO;

@FeignClient(name = "product-service", url = "${product.service.url}")
public interface ProductClient {

    @GetMapping("/api/v1/products/{id}")
    ProductResponseDTO getProductById(@PathVariable Long id);
}
