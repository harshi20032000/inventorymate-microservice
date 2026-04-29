package com.harshi_solution.auth.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.harshi_solution.auth.dto.BaseUIResponse;
import com.harshi_solution.auth.util.ResponseBuilder;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // ── Handles all custom BaseException subclasses ──────
    @ExceptionHandler(BaseException.class)
    public BaseUIResponse<Object> handleBaseException(BaseException ex) {
        // short message in response, full detail in console
        log.error("[{}] {} | detail: {}", ex.getCode(), ex.getShortMessage(), ex.getMessage());
        return ResponseBuilder.handleException(ex.getCode(), ex.getShortMessage(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseUIResponse<Object> handleValidation(MethodArgumentNotValidException ex) {
        String detail = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .reduce((a, b) -> a + " | " + b)
                .orElse("Validation failed");
        log.warn("[400] Validation error | {}", detail);
        return ResponseBuilder.handleException("400", "Validation failed", detail);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseUIResponse<Object> handleBadCredentials(BadCredentialsException ex) {
        log.warn("[401] Bad credentials attempt");
        return ResponseBuilder.handleException(
                "401", "Invalid username or password", "Authentication failed");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseUIResponse<Object> handleUsernameNotFound(UsernameNotFoundException ex) {
        log.warn("[401] Username not found: {}", ex.getMessage());
        return ResponseBuilder.handleException(
                "401", "Invalid username or password", "Authentication failed");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseUIResponse<Object> handleGeneric(Exception ex) {
        // full stack trace in console, only short message in response
        log.error("[500] Unexpected error: {}", ex.getMessage(), ex);
        return ResponseBuilder.handleException("500", "Internal server error",
                "An unexpected error occurred. Please contact support.");
    }
}
