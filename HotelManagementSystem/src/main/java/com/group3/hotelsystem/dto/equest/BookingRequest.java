package com.group3.hotelsystem.dto.equest;

import java.time.LocalDateTime;

import com.group3.hotelsystem.validation.FutureDate;

import jakarta.validation.constraints.*;

public class BookingRequest {
	@NotNull(message = "Room ID is required")
	private String roomId;

	/*
	 * @Future(message = "Check-in date must be in the future") private
	 * LocalDateTime checkInDate;
	 * 
	 * @Future(message = "Check-out date must be in the future") private
	 * LocalDateTime checkOutDate;
	 */
	
	
	//@FutureOrPresent(message = "Check-in date must be today or in the future")
    @FutureDate(message = "Check-in date must be today or in the future")
	private LocalDateTime checkInDate;

	@Future(message = "Check-out date must be in the future")
	private LocalDateTime checkOutDate;

	@Min(value = 1, message = "Number of guests must be at least 1")
	private int guests;
	public BookingRequest() {}
	public BookingRequest(@NotNull(message = "Room ID is required") String roomId,
			@Future(message = "Check-in date must be in the future") LocalDateTime checkInDate,
			@Future(message = "Check-out date must be in the future") LocalDateTime checkOutDate,
			@Min(value = 1, message = "Number of guests must be at least 1") int guests) {
		super();
		this.roomId = roomId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.guests = guests;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public LocalDateTime getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDateTime checkInDate) {
		this.checkInDate = checkInDate;
	}

	public LocalDateTime getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDateTime checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public int getGuests() {
		return guests;
	}

	public void setGuests(int guests) {
		this.guests = guests;
	}

}