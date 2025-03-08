package com.group3.hotelsystem.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.group3.hotelsystem.dto.equest.RoomRequest;
import com.group3.hotelsystem.dto.response.RoomResponse;
import com.group3.hotelsystem.exception.ResourceNotFoundException;
import com.group3.hotelsystem.mapper.RoomMapper;
import com.group3.hotelsystem.model.Admin;
import com.group3.hotelsystem.model.Room;
import com.group3.hotelsystem.repository.AdminRepository;
import com.group3.hotelsystem.repository.RoomRepository;
import com.group3.hotelsystem.service.RoomService;
import com.group3.hotelsystem.util.Constants;
import com.group3.hotelsystem.util.DateUtil;
import com.group3.hotelsystem.util.StringUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final AdminRepository adminRepository;
    private final RoomMapper roomMapper;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, AdminRepository adminRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.adminRepository = adminRepository;
        this.roomMapper = roomMapper;
    }

    @Override
    public RoomResponse addRoom(RoomRequest request, String adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ADMIN_NOT_FOUND));

        // Validate dates
        if (request.getStartDate() != null && request.getEndDate() != null && !DateUtil
                .isDateRangeValid(request.getStartDate().atStartOfDay(), request.getEndDate().atStartOfDay())) {
            throw new IllegalArgumentException(Constants.INVALID_DATE_RANGE);
        }

        Room room = roomMapper.toEntity(request);
        room.setRoomId(StringUtil.generateUniqueId(Constants.ROOM_ID_PREFIX));
        room.setAdmin(admin);

        return roomMapper.toResponse(roomRepository.save(room));
    }

    @Override
    public RoomResponse updateRoom(String roomId, RoomRequest request) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ROOM_NOT_FOUND));

        // Validate dates
        if (request.getStartDate() != null && request.getEndDate() != null && !DateUtil
                .isDateRangeValid(request.getStartDate().atStartOfDay(), request.getEndDate().atStartOfDay())) {
            throw new IllegalArgumentException(Constants.INVALID_DATE_RANGE);
        }

        roomMapper.updateEntity(room, request);
        return roomMapper.toResponse(roomRepository.save(room));
    }

    @Override
    public void deleteRoom(String roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new ResourceNotFoundException(Constants.ROOM_NOT_FOUND);
        }
        roomRepository.deleteById(roomId);
    }

	/*
	 * @Override public List<RoomResponse> findAvailableRooms(LocalDate checkIn,
	 * LocalDate checkOut, int adults, int children) { return
	 * roomRepository.findAvailableRooms(checkIn, checkOut, adults,
	 * children).stream() .map(roomMapper::toResponse).collect(Collectors.toList());
	 * }
	 */
    
    @Override
    public List<RoomResponse> findAvailableRooms(LocalDate checkIn, LocalDate checkOut, int adults, int children) {
        // Convert LocalDate to LocalDateTime for repository call
        LocalDateTime checkInDateTime = checkIn.atStartOfDay();
        LocalDateTime checkOutDateTime = checkOut.atStartOfDay();
        
        return roomRepository.findAvailableRooms(checkInDateTime, checkOutDateTime, adults, children).stream()
                .map(roomMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<RoomResponse> findRoomsByAdmin(String adminId) {
        return roomRepository.findByAdmin_AdminId(adminId).stream().map(roomMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomResponse> findRoomsWithFilters(Map<String, Object> filters) {
        return roomRepository.findRoomsWithFilters(filters).stream().map(roomMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public RoomResponse findById(String roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ROOM_NOT_FOUND));
        return roomMapper.toResponse(room);
    }
    
    @Override
    public List<RoomResponse> findAll() {
        return roomRepository.findAll().stream()
                .map(roomMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public long count() {
        return roomRepository.count();
    }
}