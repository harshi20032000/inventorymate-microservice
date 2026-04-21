package com.harshi_solution.product.service;

import java.util.List;

import com.harshi_solution.product.dto.ProductRequestDTO;
import com.harshi_solution.product.dto.ProductResponseDTO;

public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO request);

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductById(Long productId);

    ProductResponseDTO updateProduct(Long productId, ProductRequestDTO request);

    void deleteProduct(Long productId);
}
