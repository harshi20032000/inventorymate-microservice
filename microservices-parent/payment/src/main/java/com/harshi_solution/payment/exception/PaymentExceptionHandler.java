package com.harshi_solution.payment.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.harshi_solution.auth.dto.BaseUIResponse;
import com.harshi_solution.auth.exception.GlobalExceptionHandler;
import com.harshi_solution.auth.util.ResponseBuilder;

@RestControllerAdvice
public class PaymentExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(PaymentNotFoundException.class)
    public BaseUIResponse<Object> handlePaymentNotFound(PaymentNotFoundException ex) {
        return ResponseBuilder.handleException(
            ex.getCode(), ex.getShortMessage(), ex.getMessage());
    }
}
