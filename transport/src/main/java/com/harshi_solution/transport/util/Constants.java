package com.harshi_solution.transport.util;

import org.springframework.stereotype.Component;

@Component
public class Constants {
    public static final String STATUS_SUCCESS_CODE = "0";

    public static final String STATUS_SUCCESS = "SUCCESS";

    public static final String CURRENT_DATE = "currentDate";

    public static final String ERROR = "error";

    public static final String MSG = "message";

    public static final String ERROR_MESSAGE = "errorMessage";

    public static final String CORELATION_REQUEST_HEADERS = "correlationId";

    // public constructor to prevent instantiation
    public Constants() {
    }
}
