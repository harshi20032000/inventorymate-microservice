package com.harshi_solution.order.exception;

import com.harshi_solution.auth.exception.NotFoundException;

public class OrderNotFoundException extends NotFoundException {
    public OrderNotFoundException(Long id) {
        super("Order", id);
    }
}
