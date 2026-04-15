package com.harshi_solution.payment.util;

import java.util.List;
import java.util.UUID;

public class CommonUtil {

    // Check if a String is null or empty after trimming whitespace
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    // Check if a String is null or only contains whitespace
    public static boolean isNullOrBlank(String str) {
        return str == null || str.trim().isEmpty(); // Simplified, as both checks can be combined
    }

    // Check if a List is null or empty
    public static <T> boolean isListNullOrEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    // Check if an object is null
    public static boolean isNull(Object o) {
        return o == null;
    }

    // Generate a random UUID and return it as a string
    public static String getRandomId() {
        return UUID.randomUUID().toString();
    }

    public static String extractErrorCode(String errorKey) {
        return errorKey.substring(errorKey.lastIndexOf("_") + 1);
    }
}
