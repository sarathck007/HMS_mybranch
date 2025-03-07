package com.wk6.assignment4.controller;

import com.wk6.assignment4.dto.request.BookingRequest;
import com.wk6.assignment4.dto.response.ApiResponse;
import com.wk6.assignment4.dto.response.BookingResponse;
import com.wk6.assignment4.security.SecurityUtil;
import com.wk6.assignment4.service.BookingService;
import com.wk6.assignment4.service.RoomService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
//@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final SecurityUtil securityUtil;

    @Autowired
    public BookingController(BookingService bookingService, SecurityUtil securityUtil) {
        this.bookingService = bookingService;
        this.securityUtil = securityUtil;
    }
    @PostMapping
    public ResponseEntity<ApiResponse<BookingResponse>> createBooking(
            @Valid @RequestBody BookingRequest request) {
        
        String userId = securityUtil.getCurrentUserId();
        BookingResponse booking = bookingService.createBooking(request, userId);
        
        return ResponseEntity.ok(
            ApiResponse.<BookingResponse>builder()
                .success(true)
                .message("Booking created successfully")
                .data(booking)
                .build()
        );
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<BookingResponse>> updateBooking(
            @PathVariable String bookingId,
            @Valid @RequestBody BookingRequest request) {
        
        BookingResponse booking = bookingService.updateBooking(bookingId, request);
        
        return ResponseEntity.ok(
            ApiResponse.<BookingResponse>builder()
                .success(true)
                .message("Booking updated successfully")
                .data(booking)
                .build()
        );
    }

    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelBooking(@PathVariable String bookingId) {
        bookingService.cancelBooking(bookingId);
        
        return ResponseEntity.ok(
            ApiResponse.<Void>builder()
                .success(true)
                .message("Booking cancelled successfully")
                .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getUserBookings() {
        String userId = securityUtil.getCurrentUserId();
        List<BookingResponse> bookings = bookingService.findUserBookings(userId);
        
        return ResponseEntity.ok(
            ApiResponse.<List<BookingResponse>>builder()
                .success(true)
                .message("User bookings retrieved successfully")
                .data(bookings)
                .build()
        );
    }
}