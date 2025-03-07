package com.wk6.assignment4.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.*;

public class RoomRequest {
	@NotBlank(message = "Room name is required")
	private String roomName;

	@NotNull(message = "Actual price is required")
	@DecimalMin(value = "0.0", message = "Price must be positive")
	private BigDecimal actualPrice;

	@DecimalMin(value = "0.0", message = "Discounted price must be positive")
	private BigDecimal discountedPrice;

	private String features;

	@Future(message = "Start date must be in the future")
	private LocalDate startDate;

	@Future(message = "End date must be in the future")
	private LocalDate endDate;

	@Min(value = 1, message = "Maximum adults must be at least 1")
	private int maxAdults;

	@Min(value = 0, message = "Maximum children cannot be negative")
	private int maxChildren;

	public RoomRequest(@NotBlank(message = "Room name is required") String roomName,
			@NotNull(message = "Actual price is required") @DecimalMin(value = "0.0", message = "Price must be positive") BigDecimal actualPrice,
			@DecimalMin(value = "0.0", message = "Discounted price must be positive") BigDecimal discountedPrice,
			String features, @Future(message = "Start date must be in the future") LocalDate startDate,
			@Future(message = "End date must be in the future") LocalDate endDate,
			@Min(value = 1, message = "Maximum adults must be at least 1") int maxAdults,
			@Min(value = 0, message = "Maximum children cannot be negative") int maxChildren) {
		super();
		this.roomName = roomName;
		this.actualPrice = actualPrice;
		this.discountedPrice = discountedPrice;
		this.features = features;
		this.startDate = startDate;
		this.endDate = endDate;
		this.maxAdults = maxAdults;
		this.maxChildren = maxChildren;
	}
	public RoomRequest() {}
	
	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public BigDecimal getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(BigDecimal actualPrice) {
		this.actualPrice = actualPrice;
	}

	public BigDecimal getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(BigDecimal discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public int getMaxAdults() {
		return maxAdults;
	}

	public void setMaxAdults(int maxAdults) {
		this.maxAdults = maxAdults;
	}

	public int getMaxChildren() {
		return maxChildren;
	}

	public void setMaxChildren(int maxChildren) {
		this.maxChildren = maxChildren;
	}

}
