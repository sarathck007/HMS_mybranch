package com.group3.hotelsystem.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.group3.hotelsystem.dto.equest.RoomRequest;
import com.group3.hotelsystem.dto.response.RoomResponse;

public interface RoomService {
    RoomResponse addRoom(RoomRequest request, String adminId);
    RoomResponse updateRoom(String roomId, RoomRequest request);
    void deleteRoom(String roomId);
    List<RoomResponse> findAvailableRooms(LocalDate checkIn, LocalDate checkOut, int adults, int children);
    List<RoomResponse> findRoomsByAdmin(String adminId);
    List<RoomResponse> findRoomsWithFilters(Map<String, Object> filters);
    
    // New methods
    RoomResponse findById(String roomId);
    List<RoomResponse> findAll();
    long count();
}