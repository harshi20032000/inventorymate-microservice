package com.harshi_solution.user.util;

import com.harshi_solution.user.dto.BaseUIResponse;

public class ResponseBuilder {

    public static <T> BaseUIResponse<T> success(String message, T payload) {
        BaseUIResponse<T> response = new BaseUIResponse<>();
        response.setCode("200");
        response.setMessage(message);
        response.setStatus("SUCCESS");
        response.setHasError(false);
        response.setResponsePayload(payload);
        return response;
    }

    public static <T> BaseUIResponse<T> handleException(
            String code,
            String message,
            String extendedMessage) {

        BaseUIResponse<T> response = new BaseUIResponse<>();
        response.setCode(code);
        response.setMessage(message);
        response.setExtendedMessage(extendedMessage);
        response.setStatus("Exception Occured.");
        response.setHasError(true);
        response.setResponsePayload(null);
        return response;
    }
}
