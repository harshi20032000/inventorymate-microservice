package com.harshi_solution.order.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.harshi_solution.order.dto.BaseUIResponse;
import com.harshi_solution.order.exception.customExceptionHandler.OrderNotFoundException;
import com.harshi_solution.order.util.ResponseBuilder;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public BaseUIResponse<Object> handleOrderNotFound(
            OrderNotFoundException ex) {
        return ResponseBuilder.handleException("404", "Order not found", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseUIResponse<Object> handleGenericException(
            Exception ex) {
        return ResponseBuilder.handleException("500", "Internal Server Error", ex.getMessage());
    }
}
