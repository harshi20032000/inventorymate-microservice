package com.harshi_solution.party.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.harshi_solution.party.dto.BaseUIResponse;
import com.harshi_solution.party.util.ResponseBuilder;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PartyNotFoundException.class)
    public BaseUIResponse<Object> handlePartyNotFoundException(PartyNotFoundException ex) {
        BaseUIResponse<Object> result = new BaseUIResponse<>();
        result=ResponseBuilder.handleException("404", "Party Not Found", ex.getMessage());
        return result;
    }

    @ExceptionHandler(Exception.class)
    public BaseUIResponse<Object> handleGenericException(Exception ex){
        return ResponseBuilder.handleException("500", "Internal Server Error", ex.getMessage());
    }
}
