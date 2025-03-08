package com.group3.hotelsystem.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.group3.hotelsystem.model.BookingStatus;

public class BookingResponse {
	private String bookingId;
	private String userId;
	private String roomId;
	private String roomName;
	private LocalDateTime checkInDate;
	private LocalDateTime checkOutDate;
	private BigDecimal totalPrice;
	private int guests;
	private BookingStatus status;
	private String userName;

	// Default constructor
	public BookingResponse() {
	}

	// All-args constructor
	public BookingResponse(String bookingId, String userId, String roomId, String roomName, LocalDateTime checkInDate,
			LocalDateTime checkOutDate, BigDecimal totalPrice, int guests, BookingStatus status, String userName) {
		this.bookingId = bookingId;
		this.userId = userId;
		this.roomId = roomId;
		this.roomName = roomName;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.totalPrice = totalPrice;
		this.guests = guests;
		this.status = status;
		this.userName = userName;
	}

	// Static builder method
	public static BookingResponseBuilder builder() {
		return new BookingResponseBuilder();
	}

	// Getters and setters
	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
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

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getGuests() {
		return guests;
	}

	public void setGuests(int guests) {
		this.guests = guests;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	// Builder class
	public static class BookingResponseBuilder {
		private String bookingId;
		private String userId;
		private String roomId;
		private String roomName;
		private LocalDateTime checkInDate;
		private LocalDateTime checkOutDate;
		private BigDecimal totalPrice;
		private int guests;
		private BookingStatus status;
		private String userName;

		public BookingResponseBuilder bookingId(String bookingId) {
			this.bookingId = bookingId;
			return this;
		}

		public BookingResponseBuilder userId(String userId) {
			this.userId = userId;
			return this;
		}

		public BookingResponseBuilder roomId(String roomId) {
			this.roomId = roomId;
			return this;
		}

		public BookingResponseBuilder roomName(String roomName) {
			this.roomName = roomName;
			return this;
		}

		public BookingResponseBuilder checkInDate(LocalDateTime checkInDate) {
			this.checkInDate = checkInDate;
			return this;
		}

		public BookingResponseBuilder checkOutDate(LocalDateTime checkOutDate) {
			this.checkOutDate = checkOutDate;
			return this;
		}

		public BookingResponseBuilder totalPrice(BigDecimal totalPrice) {
			this.totalPrice = totalPrice;
			return this;
		}

		public BookingResponseBuilder guests(int guests) {
			this.guests = guests;
			return this;
		}

		public BookingResponseBuilder status(BookingStatus status) {
			this.status = status;
			return this;
		}

		public BookingResponseBuilder userName(String userName) {
			this.userName = userName;
			return this;
		}

		public BookingResponse build() {
			BookingResponse response = new BookingResponse();
			response.setBookingId(this.bookingId);
			response.setUserId(this.userId);
			response.setRoomId(this.roomId);
			response.setRoomName(this.roomName);
			response.setCheckInDate(this.checkInDate);
			response.setCheckOutDate(this.checkOutDate);
			response.setTotalPrice(this.totalPrice);
			response.setGuests(this.guests);
			response.setStatus(this.status);
			response.setUserName(this.userName);
			return response;
		}
	}
}