package com.harshi_solution.product.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.harshi_solution.product.dto.BaseUIResponse;
import com.harshi_solution.product.util.ResponseBuilder;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public BaseUIResponse<Object> handleProductNotFoundException(ProductNotFoundException ex) {
        return ResponseBuilder.handleException("404", "Product Not Found", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseUIResponse<Object> handleGenericException(Exception ex) {
        return ResponseBuilder.handleException("500", "Internal Server Error", ex.getMessage());
    }
}
