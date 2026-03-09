package com.harshi_solution.warehouse.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.harshi_solution.warehouse.dto.BaseUIResponse;
import com.harshi_solution.warehouse.util.ResponseBuilder;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientStockException.class)
    public BaseUIResponse<Object> insufficientStockException(
            InsufficientStockException ex) {
                return ResponseBuilder.handleException("404", "Insufficient Stock", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseUIResponse<Object> handleGenericException(Exception ex){
        return ResponseBuilder.handleException("500", "Internal Server", ex.getMessage());
    }
}
