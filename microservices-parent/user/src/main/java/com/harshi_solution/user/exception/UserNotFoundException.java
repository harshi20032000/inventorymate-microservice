package com.harshi_solution.user.exception;

import com.harshi_solution.auth.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long id) {
        super("User", id);
    }
}
