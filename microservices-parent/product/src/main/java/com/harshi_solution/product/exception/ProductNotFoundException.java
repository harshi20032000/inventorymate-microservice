package com.harshi_solution.product.exception;

import com.harshi_solution.auth.exception.NotFoundException;

public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException(Long id) {
        super("Product", id);
    }
}
