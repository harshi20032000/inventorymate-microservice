package com.harshi_solution.warehouse.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.harshi_solution.auth.dto.BaseUIResponse;
import com.harshi_solution.auth.exception.GlobalExceptionHandler;
import com.harshi_solution.auth.util.ResponseBuilder;

@RestControllerAdvice
public class WarehouseExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(WarehouseNotFoundException.class)
    public BaseUIResponse<Object> handleWarehouseNotFound(WarehouseNotFoundException ex) {
        return ResponseBuilder.handleException(
            ex.getCode(), ex.getShortMessage(), ex.getMessage());
    }

     @ExceptionHandler(InsufficientStockException.class)
    public BaseUIResponse<Object> handleStockException(
            InsufficientStockException ex) {

        BaseUIResponse<Object> response = new BaseUIResponse<>();
        response.setCode("STOCK_ERROR");
        response.setMessage("Insufficient stock");
        response.setExtendedMessage(ex.getMessage());
        response.setStatus("FAILED");
        response.setHasError(true);

        return response;
    }
}
