package com.harshi_solution.warehouse.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.harshi_solution.warehouse.dto.BaseUIResponse;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<BaseUIResponse<Object>> handleStockException(
            InsufficientStockException ex) {

        BaseUIResponse<Object> response = new BaseUIResponse<>();
        response.setCode("STOCK_ERROR");
        response.setMessage("Insufficient stock");
        response.setExtendedMessage(ex.getMessage());
        response.setStatus("FAILED");
        response.setHasError(true);

        return ResponseEntity
                .badRequest()
                .body(response);
    }
}
