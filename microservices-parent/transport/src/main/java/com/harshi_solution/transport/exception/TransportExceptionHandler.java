package com.harshi_solution.transport.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.harshi_solution.auth.dto.BaseUIResponse;
import com.harshi_solution.auth.exception.GlobalExceptionHandler;
import com.harshi_solution.auth.util.ResponseBuilder;

@RestControllerAdvice
public class TransportExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(TransportNotFoundException.class)
    public BaseUIResponse<Object> handleTransportNotFound(TransportNotFoundException ex) {
        return ResponseBuilder.handleException(
            ex.getCode(), ex.getShortMessage(), ex.getMessage());
    }
}
