package com.harshi_solution.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harshi_solution.product.dto.ProductRequestDTO;
import com.harshi_solution.product.dto.ProductResponseDTO;
import com.harshi_solution.product.entities.Product;
import com.harshi_solution.product.repo.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO request) {

        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setProductCode(request.getProductCode());
        product.setBasePrice(request.getBasePrice());
        product.setPType(request.getpType());

        Product saved = productRepository.save(product);

        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO getProductById(Long productId) {

        Product product = findProductOrThrow(productId);
        return mapToResponse(product);
    }

    @Override
    public ProductResponseDTO updateProduct(Long productId, ProductRequestDTO request) {

        Product product = findProductOrThrow(productId);

        product.setProductName(request.getProductName());
        product.setProductCode(request.getProductCode());
        product.setBasePrice(request.getBasePrice());
        product.setPType(request.getpType());

        Product updated = productRepository.save(product);

        return mapToResponse(updated);
    }

    @Override
    public void deleteProduct(Long productId) {

        Product product = findProductOrThrow(productId);
        productRepository.delete(product);
    }

    // -------------------------------
    // 🔹 Private helper methods
    // -------------------------------

    private Product findProductOrThrow(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
    }

    private ProductResponseDTO mapToResponse(Product product) {

        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setProductCode(product.getProductCode());
        dto.setBasePrice(product.getBasePrice());
        dto.setpType(product.getPType());

        return dto;
    }
}
