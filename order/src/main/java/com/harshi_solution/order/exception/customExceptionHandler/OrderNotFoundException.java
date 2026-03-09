package com.harshi_solution.order.exception.customExceptionHandler;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String message) {
        super(message);
    }
}
