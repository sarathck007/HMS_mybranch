package com.group3.hotelsystem.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.group3.hotelsystem.dto.equest.BookingRequest;
import com.group3.hotelsystem.dto.response.BookingResponse;
import com.group3.hotelsystem.model.BookingStatus;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request, String userId);
    BookingResponse updateBooking(String bookingId, BookingRequest request);
    void cancelBooking(String bookingId);
    List<BookingResponse> findUserBookings(String userId);
    List<BookingResponse> findBookingsByStatus(BookingStatus status);
    List<Map<String, Object>> getMonthlyIncome();
    boolean isRoomAvailable(String roomId, LocalDateTime checkIn, LocalDateTime checkOut);
    
    // New methods
    BookingResponse findById(String bookingId);
    BookingResponse updateBookingStatus(String bookingId, BookingStatus status);
}