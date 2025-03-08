package com.group3.hotelsystem.util;

import java.util.UUID;
import org.springframework.util.StringUtils;

public class StringUtil {
	public static String generateUniqueId(String prefix) {
        return prefix + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8);
    }

    public static String formatPhoneNumber(String phoneNumber) {
        if (!StringUtils.hasText(phoneNumber)) {
            return null;
        }
        // Remove all non-digit characters
        String digits = phoneNumber.replaceAll("\\D", "");
        // Format as per requirement (example: +1-234-567-8900)
        if (digits.length() >= 10) {
            return String.format("+%s-%s-%s-%s",
                digits.substring(0, 1),
                digits.substring(1, 4),
                digits.substring(4, 7),
                digits.substring(7, Math.min(11, digits.length())));
        }
        return phoneNumber;
    }

    public static String sanitizeString(String input) {
        if (input == null) {
            return null;
        }
        return input.trim().replaceAll("\\s+", " ");
    }
}
