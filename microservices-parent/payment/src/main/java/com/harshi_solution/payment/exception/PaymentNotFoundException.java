package com.harshi_solution.payment.exception;

import com.harshi_solution.auth.exception.NotFoundException;

public class PaymentNotFoundException extends NotFoundException {
    public PaymentNotFoundException(Long id) {
        super("Payment", id);
    }
}
