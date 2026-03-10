package com.harshi_solution.payment.exception;

public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException(String msg) {
        super(msg);
    }
}
