package com.group3.hotelsystem.util;

public class Constants {
	 // General Constants
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    // ID Prefixes
    public static final String USER_ID_PREFIX = "USR";
    public static final String ADMIN_ID_PREFIX = "ADM";
    public static final String ROOM_ID_PREFIX = "RM";
    public static final String BOOKING_ID_PREFIX = "BK";
    
    // Default Values
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final String DEFAULT_SORT_DIRECTION = "DESC";
    
    // Error Messages
    public static final String USER_NOT_FOUND = "User not found";
    public static final String ADMIN_NOT_FOUND = "Admin not found";
    public static final String ROOM_NOT_FOUND = "Room not found";
    public static final String BOOKING_NOT_FOUND = "Booking not found";
    public static final String EMAIL_EXISTS = "Email already exists";
    public static final String ROOM_NOT_AVAILABLE = "Room is not available for the selected dates";
    
    // Validation Messages
    public static final String INVALID_DATE_RANGE = "Check-out date must be after check-in date";
    public static final String INVALID_GUEST_COUNT = "Guest count exceeds room capacity";
    
    // Security Constants
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long TOKEN_EXPIRATION = 864_000_000; // 10 days
    
    private Constants() {
        // Private constructor to prevent instantiation
    }
}
