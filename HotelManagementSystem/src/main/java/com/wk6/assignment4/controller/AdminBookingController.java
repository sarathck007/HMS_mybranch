package com.wk6.assignment4.controller;

import com.wk6.assignment4.dto.request.BookingRequest;
import com.wk6.assignment4.dto.response.ApiResponse;
import com.wk6.assignment4.dto.response.BookingResponse;
import com.wk6.assignment4.model.BookingStatus;
import com.wk6.assignment4.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/bookings")
@PreAuthorize("hasRole('ADMIN')")
//@RequiredArgsConstructor
public class AdminBookingController {
    private final BookingService bookingService;
  

    @Autowired
    public AdminBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getAllBookings() {
        List<BookingResponse> bookings = bookingService.findBookingsByStatus(null);
        
        return ResponseEntity.ok(
            ApiResponse.<List<BookingResponse>>builder()
                .success(true)
                .message("All bookings retrieved successfully")
                .data(bookings)
                .build()
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getBookingsByStatus(
            @PathVariable BookingStatus status) {
        
        List<BookingResponse> bookings = bookingService.findBookingsByStatus(status);
        
        return ResponseEntity.ok(
            ApiResponse.<List<BookingResponse>>builder()
                .success(true)
                .message("Bookings retrieved successfully")
                .data(bookings)
                .build()
        );
    }

    @GetMapping("/reports/monthly-income")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getMonthlyIncome() {
        List<Map<String, Object>> income = bookingService.getMonthlyIncome();
        
        return ResponseEntity.ok(
            ApiResponse.<List<Map<String, Object>>>builder()
                .success(true)
                .message("Monthly income report generated successfully")
                .data(income)
                .build()
        );
    }
}
