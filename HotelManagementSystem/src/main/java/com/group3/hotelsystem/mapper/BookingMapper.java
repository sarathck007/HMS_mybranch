package com.group3.hotelsystem.mapper;

import org.springframework.stereotype.Component;

import com.group3.hotelsystem.dto.equest.BookingRequest;
import com.group3.hotelsystem.dto.response.BookingResponse;
import com.group3.hotelsystem.model.Booking;

@Component
public class BookingMapper {
    
    public BookingResponse toResponse(Booking booking) {
        return BookingResponse.builder()
                .bookingId(booking.getBookingId())
                .userId(booking.getUser().getUserId())  // Add userId to response
                .roomId(booking.getRoom().getRoomId())
                .roomName(booking.getRoom().getRoomName())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .totalPrice(booking.getTotalPrice())
                .guests(booking.getGuests())
                .status(booking.getStatus())
                .userName(booking.getUser().getFirstName() + " " + booking.getUser().getLastName())
                .build();
    }

    public void updateEntity(Booking booking, BookingRequest request) {
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setGuests(request.getGuests());
    }
}