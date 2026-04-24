package com.harshi_solution.auth.exception;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException(String message) {
        super("401", "Unauthorized", message);
    }
}
