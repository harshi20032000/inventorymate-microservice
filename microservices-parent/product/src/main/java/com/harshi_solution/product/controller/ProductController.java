package com.harshi_solution.product.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harshi_solution.product.dto.BaseUIResponse;
import com.harshi_solution.product.dto.ProductRequestDTO;
import com.harshi_solution.product.dto.ProductResponseDTO;
import com.harshi_solution.product.service.ProductService;
import com.harshi_solution.product.util.ResponseBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public BaseUIResponse<ProductResponseDTO> createProduct(
            @Valid @RequestBody ProductRequestDTO request) {

        ProductResponseDTO created = productService.createProduct(request);

        return ResponseBuilder.success(
                "Product created successfully",
                created);
    }

    @GetMapping
    public BaseUIResponse<List<ProductResponseDTO>> getAllProducts() {

        List<ProductResponseDTO> products = productService.getAllProducts();

        return ResponseBuilder.success(
                "Products fetched successfully",
                products);
    }

    @GetMapping("/{id}")
    public BaseUIResponse<ProductResponseDTO> getProductById(
            @PathVariable Long id) {

        ProductResponseDTO product = productService.getProductById(id);

        return ResponseBuilder.success(
                "Product fetched successfully",
                product);
    }

    @PutMapping("/{id}")
    public BaseUIResponse<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO request) {

        ProductResponseDTO updated = productService.updateProduct(id, request);

        return ResponseBuilder.success(
                "Product updated successfully",
                updated);
    }

    @DeleteMapping("/{id}")
    public BaseUIResponse<Void> deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseBuilder.success(
                "Product deleted successfully",
                null);
    }
}
