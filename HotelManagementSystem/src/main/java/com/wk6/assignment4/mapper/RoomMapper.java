package com.wk6.assignment4.mapper;

import org.springframework.stereotype.Component;

import com.wk6.assignment4.dto.request.RoomRequest;
import com.wk6.assignment4.dto.response.RoomResponse;
import com.wk6.assignment4.model.Room;

@Component
public class RoomMapper {
    
    public Room toEntity(RoomRequest request) {
        return Room.builder()
                .roomName(request.getRoomName())
                .actualPrice(request.getActualPrice())
                .discountedPrice(request.getDiscountedPrice())
                .features(request.getFeatures())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .maxAdults(request.getMaxAdults())
                .maxChildren(request.getMaxChildren())
                .build();
    }
    
    public RoomResponse toResponse(Room room) {
        return RoomResponse.builder()
                .roomId(room.getRoomId())
                .roomName(room.getRoomName())
                .actualPrice(room.getActualPrice())
                .discountedPrice(room.getDiscountedPrice())
                .features(room.getFeatures())
                .maxAdults(room.getMaxAdults())
                .maxChildren(room.getMaxChildren())
             // Add these fields
                .startDate(room.getStartDate())
                .endDate(room.getEndDate())
                .build();
    }

    public void updateEntity(Room room, RoomRequest request) {
        room.setRoomName(request.getRoomName());
        room.setActualPrice(request.getActualPrice());
        room.setDiscountedPrice(request.getDiscountedPrice());
        room.setFeatures(request.getFeatures());
        room.setStartDate(request.getStartDate());
        room.setEndDate(request.getEndDate());
        room.setMaxAdults(request.getMaxAdults());
        room.setMaxChildren(request.getMaxChildren());
    }
}
