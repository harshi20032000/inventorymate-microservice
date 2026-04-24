package com.harshi_solution.order.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.harshi_solution.auth.dto.BaseUIResponse;
import com.harshi_solution.auth.exception.GlobalExceptionHandler;
import com.harshi_solution.auth.util.ResponseBuilder;

@RestControllerAdvice
public class OrderExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public BaseUIResponse<Object> handleOrderNotFound(OrderNotFoundException ex) {
        return ResponseBuilder.handleException(
            ex.getCode(), ex.getShortMessage(), ex.getMessage());
    }
}