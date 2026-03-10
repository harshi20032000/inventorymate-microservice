package com.harshi_solution.transport.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.harshi_solution.transport.dto.BaseUIResponse;
import com.harshi_solution.transport.util.ResponseBuilder;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TransportNotFoundException.class)
    public BaseUIResponse<Object> handleTransportNotFoundException(TransportNotFoundException ex) {
        BaseUIResponse<Object> result = new BaseUIResponse<>();
        result=ResponseBuilder.handleException("404", "Transport Not Found", ex.getMessage());
        return result;
    }

    @ExceptionHandler(Exception.class)
    public BaseUIResponse<Object> handleGenericException(Exception ex){
        return ResponseBuilder.handleException("500", "Internal Server Error", ex.getMessage());
    }
}
