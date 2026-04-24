package com.harshi_solution.auth.exception;


public class BaseException extends RuntimeException {

    private final String code;
    private final String shortMessage;

    public BaseException(String code, String shortMessage, String detailMessage) {
        super(detailMessage);
        this.code         = code;
        this.shortMessage = shortMessage;
    }

    public String getCode()         { return code; }
    public String getShortMessage() { return shortMessage; }
}
