package com.harshi_solution.auth.exception;

public class NotFoundException extends BaseException {
    public NotFoundException(String entity, Object id) {
        super("404", entity + " not found",
                entity + " with id " + id + " was not found");
    }

    public NotFoundException(String message) {
        super("404", "Not found", message);
    }
}
