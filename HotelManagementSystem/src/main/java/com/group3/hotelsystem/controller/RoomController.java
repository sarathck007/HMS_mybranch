package com.group3.hotelsystem.controller;

import com.group3.hotelsystem.dto.equest.RoomRequest;
import com.group3.hotelsystem.dto.response.ApiResponse;
import com.group3.hotelsystem.dto.response.RoomResponse;
import com.group3.hotelsystem.security.SecurityUtil;
import com.group3.hotelsystem.service.QueryService;
import com.group3.hotelsystem.service.RoomService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rooms")
//@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
    private final SecurityUtil securityUtil;
    
    @Autowired
    public RoomController(RoomService roomService, SecurityUtil securityUtil) {
        this.roomService = roomService;
        this.securityUtil = securityUtil;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoomResponse>>> getAllRooms() {
        // For users, get all available rooms
        List<RoomResponse> rooms = roomService.findAvailableRooms(
                LocalDate.now(), 
                LocalDate.now().plusMonths(6), 
                1, 0);
        
        return ResponseEntity.ok(
            ApiResponse.<List<RoomResponse>>builder()
                .success(true)
                .message("Rooms retrieved successfully")
                .data(rooms)
                .build()
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<RoomResponse>>> searchRooms(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
            @RequestParam int adults,
            @RequestParam int children) {
        
        List<RoomResponse> rooms = roomService.findAvailableRooms(checkIn, checkOut, adults, children);
        
        return ResponseEntity.ok(
            ApiResponse.<List<RoomResponse>>builder()
                .success(true)
                .message("Rooms retrieved successfully")
                .data(rooms)
                .build()
        );
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<RoomResponse>>> filterRooms(
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer maxAdults,
            @RequestParam(required = false) String features) {
        
        Map<String, Object> filters = new HashMap<>();
        if (minPrice != null) filters.put("minPrice", minPrice);
        if (maxPrice != null) filters.put("maxPrice", maxPrice);
        if (maxAdults != null) filters.put("maxAdults", maxAdults);
        if (features != null) filters.put("features", features);
        
        List<RoomResponse> rooms = roomService.findRoomsWithFilters(filters);
        
        return ResponseEntity.ok(
            ApiResponse.<List<RoomResponse>>builder()
                .success(true)
                .message("Rooms retrieved successfully")
                .data(rooms)
                .build()
        );
    }
}
