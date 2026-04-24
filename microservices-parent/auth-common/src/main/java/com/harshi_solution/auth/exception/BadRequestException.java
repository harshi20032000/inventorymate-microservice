package com.harshi_solution.auth.exception;

public class BadRequestException extends BaseException {
    public BadRequestException(String message) {
        super("400", "Bad request", message);
    }
}
