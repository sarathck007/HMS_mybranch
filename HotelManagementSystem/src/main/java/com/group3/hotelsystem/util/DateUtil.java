package com.group3.hotelsystem.util;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
public class DateUtil {
	 private static final DateTimeFormatter DATE_FORMATTER = 
		        DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
		    private static final DateTimeFormatter DATE_TIME_FORMATTER = 
		        DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT);

		    public static String formatDate(LocalDate date) {
		        return date != null ? date.format(DATE_FORMATTER) : null;
		    }

		    public static String formatDateTime(LocalDateTime dateTime) {
		        return dateTime != null ? dateTime.format(DATE_TIME_FORMATTER) : null;
		    }

		    public static LocalDate parseDate(String dateStr) {
		        return dateStr != null ? LocalDate.parse(dateStr, DATE_FORMATTER) : null;
		    }

		    public static LocalDateTime parseDateTime(String dateTimeStr) {
		        return dateTimeStr != null ? LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER) : null;
		    }

		    public static long calculateNights(LocalDateTime checkIn, LocalDateTime checkOut) {
		        return ChronoUnit.DAYS.between(checkIn.toLocalDate(), checkOut.toLocalDate());
		    }

		    public static boolean isDateRangeValid(LocalDateTime checkIn, LocalDateTime checkOut) {
		        return checkIn != null && checkOut != null && checkOut.isAfter(checkIn);
		    }

		    public static boolean isDateInRange(LocalDateTime date, LocalDateTime startDate, LocalDateTime endDate) {
		        return !date.isBefore(startDate) && !date.isAfter(endDate);
		    }
}
