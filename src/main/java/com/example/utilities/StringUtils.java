package com.example.utilities;

import java.util.UUID;

/**
 * Utility class for string operations.
 */
public class StringUtils {

    private StringUtils() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Check if a string is null or empty.
     *
     * @param str the string to check
     * @return true if the string is null or empty, false otherwise
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * Check if a string is not null and not empty.
     *
     * @param str the string to check
     * @return true if the string is not null and not empty, false otherwise
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    
    /**
     * Generate a random UUID as string.
     *
     * @return a random UUID string
     */
    public static String generateUuid() {
        return UUID.randomUUID().toString();
    }
    
    /**
     * Truncate a string to a maximum length.
     *
     * @param str the string to truncate
     * @param maxLength the maximum length
     * @return the truncated string
     */
    public static String truncate(String str, int maxLength) {
        if (str == null) {
            return null;
        }
        return str.length() <= maxLength ? str : str.substring(0, maxLength);
    }
}
