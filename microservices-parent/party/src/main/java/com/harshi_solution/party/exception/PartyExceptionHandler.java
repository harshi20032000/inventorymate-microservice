package com.harshi_solution.party.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.harshi_solution.auth.dto.BaseUIResponse;
import com.harshi_solution.auth.exception.GlobalExceptionHandler;
import com.harshi_solution.auth.util.ResponseBuilder;

@RestControllerAdvice
public class PartyExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(PartyNotFoundException.class)
    public BaseUIResponse<Object> handlePartyNotFound(PartyNotFoundException ex) {
        return ResponseBuilder.handleException(
            ex.getCode(), ex.getShortMessage(), ex.getMessage());
    }
}
