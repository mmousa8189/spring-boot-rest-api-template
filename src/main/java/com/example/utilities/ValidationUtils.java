package com.example.utilities;

import java.util.regex.Pattern;

/**
 * Utility class for validation operations.
 */
public class ValidationUtils {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    private ValidationUtils() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Validate an email address.
     *
     * @param email the email address to validate
     * @return true if the email is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * Validate a password strength.
     * Password must be at least 8 characters long and contain at least one digit, 
     * one lowercase letter, one uppercase letter, and one special character.
     *
     * @param password the password to validate
     * @return true if the password is strong, false otherwise
     */
    public static boolean isStrongPassword(String password) {
        if (StringUtils.isEmpty(password) || password.length() < 8) {
            return false;
        }
        
        boolean hasDigit = false;
        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasSpecial = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }
        
        return hasDigit && hasLower && hasUpper && hasSpecial;
    }
}
