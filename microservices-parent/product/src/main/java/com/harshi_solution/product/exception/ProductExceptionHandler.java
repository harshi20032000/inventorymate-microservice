package com.harshi_solution.product.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.harshi_solution.auth.dto.BaseUIResponse;
import com.harshi_solution.auth.exception.GlobalExceptionHandler;
import com.harshi_solution.auth.util.ResponseBuilder;

@RestControllerAdvice
public class ProductExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public BaseUIResponse<Object> handleProductNotFound(ProductNotFoundException ex) {
        return ResponseBuilder.handleException(
            ex.getCode(), ex.getShortMessage(), ex.getMessage());
    }
}
