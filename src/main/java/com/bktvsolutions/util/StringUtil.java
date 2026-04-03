/**
    String utilities class provides helper methods for common string operations, including null/blank checks,
    formatting, comparison, and basic manipulation, without maintaining any state.
*/

package com.bktvsolutions.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/**
    Utility methods for common String operations.
*/

public final class StringUtil {

    private StringUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
        Returns true if the value is null or empty.
    */

    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    /**
        Returns true if the value is null, empty, or contains only whitespace.
    */

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
        Returns true if the value is not null and not empty.
    */

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    /**
        Returns true if the value is not null and not blank.
    */

    public static boolean isNotBlank(String value) {
        return !isBlank(value);
    }

    /**
        Trims the value, or returns null if the value is null.
    */

    public static String trim(String value) {
        return value == null ? null : value.trim();
    }

    /**
        Trims the value, or returns an empty string if the value is null.
    */

    public static String trimToEmpty(String value) {
        return value == null ? "" : value.trim();
    }

    /**
        Trims the value and returns null if the result is empty.
    */

    public static String trimToNull(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    /**
        Returns an empty string if the value is null.
    */

    public static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    /**
        Returns null if the value is empty.
    */

    public static String emptyToNull(String value) {
        return isEmpty(value) ? null : value;
    }

    /**
        Returns null if the value is blank.
    */

    public static String blankToNull(String value) {
        return isBlank(value) ? null : value;
    }

    /**
        Returns the default value if the input is null.
    */

    public static String defaultIfNull(String value, String defaultValue) {
        return value == null ? defaultValue : value;
    }

    /**
        Returns the default value if the input is empty.
    */

    public static String defaultIfEmpty(String value, String defaultValue) {
        return isEmpty(value) ? defaultValue : value;
    }

    /**
        Returns the default value if the input is blank.
    */

    public static String defaultIfBlank(String value, String defaultValue) {
        return isBlank(value) ? defaultValue : value;
    }

    /**
        Null-safe equality check.
    */

    public static boolean equals(String first, String second) {
        return Objects.equals(first, second);
    }

    /**
        Null-safe case-insensitive equality check.
    */

    public static boolean equalsIgnoreCase(String first, String second) {
        if (first == null || second == null) {
            return first == second;
        }

        return first.equalsIgnoreCase(second);
    }

    /**
        Returns true if the value contains the search value.
    */

    public static boolean contains(String value, String searchValue) {
        if (value == null || searchValue == null) {
            return false;
        }

        return value.contains(searchValue);
    }

    /**
        Returns true if the value contains the search value, ignoring case.
    */

    public static boolean containsIgnoreCase(String value, String searchValue) {
        if (value == null || searchValue == null) {
            return false;
        }

        return value.toLowerCase().contains(searchValue.toLowerCase());
    }

    /**
        Converts the value to upper case, or returns null if the value is null.
    */

    public static String toUpperCase(String value) {
        return value == null ? null : value.toUpperCase();
    }

    /**
        Converts the value to lower case, or returns null if the value is null.
    */

    public static String toLowerCase(String value) {
        return value == null ? null : value.toLowerCase();
    }

    /**
        Capitalizes the first character after trimming the value.
    */

    public static String capitalize(String value) {
        if (isBlank(value)) {
            return value;
        }

        String trimmed = value.trim();
        return Character.toUpperCase(trimmed.charAt(0)) + trimmed.substring(1);
    }

    /**
        Makes the first character lowercase after trimming the value.
    */

    public static String uncapitalize(String value) {
        if (isBlank(value)) {
            return value;
        }

        String trimmed = value.trim();
        return Character.toLowerCase(trimmed.charAt(0)) + trimmed.substring(1);
    }

    /**
        Reverses the value, or returns null if the value is null.
    */

    public static String reverse(String value) {
        if (value == null) {
            return null;
        }

        return new StringBuilder(value).reverse().toString();
    }

    /**
        Repeats the value the given number of times.
    */

    public static String repeat(String value, int count) {
        if (value == null) {
            return null;
        }

        if (count <= 0) {
            return "";
        }

        return value.repeat(count);
    }

    /**
        Joins the values using the provided delimiter.
    */

    public static String join(Collection<?> values, String delimiter) {
        if (values == null || values.isEmpty()) {
            return "";
        }

        String actualDelimiter = delimiter == null ? "" : delimiter;
        StringBuilder builder = new StringBuilder();
        Iterator<?> iterator = values.iterator();

        while (iterator.hasNext()) {
            Object value = iterator.next();
            builder.append(value == null ? "" : value);

            if (iterator.hasNext()) {
                builder.append(actualDelimiter);
            }
        }

        return builder.toString();
    }

    /**
        Truncates the value to the given maximum length.

        @throws IllegalArgumentException if maxLength is negative
    */

    public static String truncate(String value, int maxLength) {
        if (value == null) {
            return null;
        }

        if (maxLength < 0) {
            throw new IllegalArgumentException("maxLength cannot be negative");
        }

        if (value.length() <= maxLength) {
            return value;
        }

        return value.substring(0, maxLength);
    }

    /**
        Truncates the value and appends an ellipsis if needed.

        @throws IllegalArgumentException if maxLength is less than 3
    */

    public static String truncateWithEllipsis(String value, int maxLength) {
        if (value == null) {
            return null;
        }

        if (maxLength < 3) {
            throw new IllegalArgumentException("maxLength must be at least 3");
        }

        if (value.length() <= maxLength) {
            return value;
        }

        return value.substring(0, maxLength - 3) + "...";
    }

    /**
        Pads the left side of the value until it reaches the target length.
    */

    public static String padLeft(String value, int length, char padChar) {
        if (value == null) {
            return null;
        }

        if (length <= value.length()) {
            return value;
        }

        StringBuilder builder = new StringBuilder(length);

        for (int i = value.length(); i < length; i++) {
            builder.append(padChar);
        }

        builder.append(value);
        return builder.toString();
    }

    /**
        Pads the right side of the value until it reaches the target length.
    */

    public static String padRight(String value, int length, char padChar) {
        if (value == null) {
            return null;
        }

        if (length <= value.length()) {
            return value;
        }

        StringBuilder builder = new StringBuilder(length);
        builder.append(value);

        for (int i = value.length(); i < length; i++) {
            builder.append(padChar);
        }

        return builder.toString();
    }
}