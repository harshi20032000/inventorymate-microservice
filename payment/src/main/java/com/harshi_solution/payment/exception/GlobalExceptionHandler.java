package com.harshi_solution.payment.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.harshi_solution.payment.dto.BaseUIResponse;
import com.harshi_solution.payment.util.ResponseBuilder;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentNotFoundException.class)
    public BaseUIResponse<Object> handlePaymentNotFoundException(PaymentNotFoundException ex) {
        return ResponseBuilder.handleException("404", "Payment Not Found", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseUIResponse<Object> handleGenericException(Exception ex) {
        return ResponseBuilder.handleException("500", "Internal Server Error", ex.getMessage());
    }
}
