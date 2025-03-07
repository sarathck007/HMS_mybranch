package com.wk6.assignment4.controller;

import com.wk6.assignment4.dto.request.RoomRequest;
import com.wk6.assignment4.dto.response.ApiResponse;
import com.wk6.assignment4.dto.response.RoomResponse;
import com.wk6.assignment4.security.SecurityUtil;
import com.wk6.assignment4.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/rooms")
@PreAuthorize("hasRole('ADMIN')")
public class AdminRoomController {
    private final RoomService roomService;
    private final SecurityUtil securityUtil;
    @Autowired
    public AdminRoomController(RoomService roomService, SecurityUtil securityUtil) {
        this.roomService = roomService;
        this.securityUtil = securityUtil;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoomResponse>>> getAdminRooms() {
        String adminId = securityUtil.getCurrentUserId();
        List<RoomResponse> rooms = roomService.findRoomsByAdmin(adminId);
        
        return ResponseEntity.ok(
            ApiResponse.<List<RoomResponse>>builder()
                .success(true)
                .message("Admin rooms retrieved successfully")
                .data(rooms)
                .build()
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RoomResponse>> addRoom(@Valid @RequestBody RoomRequest request) {
        String adminId = securityUtil.getCurrentUserId();
        RoomResponse room = roomService.addRoom(request, adminId);
        
        return ResponseEntity.ok(
            ApiResponse.<RoomResponse>builder()
                .success(true)
                .message("Room added successfully")
                .data(room)
                .build()
        );
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<ApiResponse<RoomResponse>> updateRoom(
            @PathVariable String roomId,
            @Valid @RequestBody RoomRequest request) {
        
        RoomResponse room = roomService.updateRoom(roomId, request);
        
        return ResponseEntity.ok(
            ApiResponse.<RoomResponse>builder()
                .success(true)
                .message("Room updated successfully")
                .data(room)
                .build()
        );
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<ApiResponse<Void>> deleteRoom(@PathVariable String roomId) {
        roomService.deleteRoom(roomId);
        
        return ResponseEntity.ok(
            ApiResponse.<Void>builder()
                .success(true)
                .message("Room deleted successfully")
                .build()
        );
    }
}