package com.example.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for date operations.
 */
public class DateUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    private DateUtils() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Format a LocalDateTime to string.
     *
     * @param dateTime the date time to format
     * @return the formatted date time string
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return DATE_FORMATTER.format(dateTime);
    }
    
    /**
     * Parse a string to LocalDateTime.
     *
     * @param dateTimeStr the date time string
     * @return the parsed LocalDateTime
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateTimeStr, DATE_FORMATTER);
    }
}
