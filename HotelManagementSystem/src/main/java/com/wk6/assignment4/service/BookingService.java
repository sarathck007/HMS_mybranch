package com.wk6.assignment4.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.wk6.assignment4.dto.request.BookingRequest;
import com.wk6.assignment4.dto.response.BookingResponse;
import com.wk6.assignment4.model.BookingStatus;

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