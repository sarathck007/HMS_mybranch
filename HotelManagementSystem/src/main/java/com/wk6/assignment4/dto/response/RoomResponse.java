package com.wk6.assignment4.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RoomResponse {
    private String roomId;
    private String roomName;
    private BigDecimal actualPrice;
    private BigDecimal discountedPrice;
    private String features;
    private int maxAdults;
    private int maxChildren;
    private boolean isAvailable;
    private String imageUrl;
    // Add these fields as LocalDate (not LocalDateTime) to match the template expectations
    private LocalDate startDate;
    private LocalDate endDate;

    // Default constructor
    public RoomResponse() {}

    // All-args constructor
    public RoomResponse(String roomId, String roomName, BigDecimal actualPrice, BigDecimal discountedPrice,
            String features, int maxAdults, int maxChildren, boolean isAvailable, String imageUrl) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.actualPrice = actualPrice;
        this.discountedPrice = discountedPrice;
        this.features = features;
        this.maxAdults = maxAdults;
        this.maxChildren = maxChildren;
        this.isAvailable = isAvailable;
        this.imageUrl = imageUrl;
    }
    // Add getters and setters for the new fields
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
    // Static builder method
    public static RoomResponseBuilder builder() {
        return new RoomResponseBuilder();
    }

    // Getters and setters
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // Builder class
    public static class RoomResponseBuilder {
        private String roomId;
        private String roomName;
        private BigDecimal actualPrice;
        private BigDecimal discountedPrice;
        private String features;
        private int maxAdults;
        private int maxChildren;
        private boolean isAvailable;
        private String imageUrl;
        private LocalDate startDate;
        private LocalDate endDate;
        
        public RoomResponseBuilder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }
        
        public RoomResponseBuilder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }
        
        public RoomResponseBuilder roomId(String roomId) {
            this.roomId = roomId;
            return this;
        }
        
        public RoomResponseBuilder roomName(String roomName) {
            this.roomName = roomName;
            return this;
        }
        
        public RoomResponseBuilder actualPrice(BigDecimal actualPrice) {
            this.actualPrice = actualPrice;
            return this;
        }
        
        public RoomResponseBuilder discountedPrice(BigDecimal discountedPrice) {
            this.discountedPrice = discountedPrice;
            return this;
        }
        
        public RoomResponseBuilder features(String features) {
            this.features = features;
            return this;
        }
        
        public RoomResponseBuilder maxAdults(int maxAdults) {
            this.maxAdults = maxAdults;
            return this;
        }
        
        public RoomResponseBuilder maxChildren(int maxChildren) {
            this.maxChildren = maxChildren;
            return this;
        }
        
        public RoomResponseBuilder isAvailable(boolean isAvailable) {
            this.isAvailable = isAvailable;
            return this;
        }
        
        public RoomResponseBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }
        
        public RoomResponse build() {
            RoomResponse response = new RoomResponse();
            response.setRoomId(this.roomId);
            response.setRoomName(this.roomName);
            response.setActualPrice(this.actualPrice);
            response.setDiscountedPrice(this.discountedPrice);
            response.setFeatures(this.features);
            response.setMaxAdults(this.maxAdults);
            response.setMaxChildren(this.maxChildren);
            response.setAvailable(this.isAvailable);
            response.setImageUrl(this.imageUrl);
         // New field setters
            response.setStartDate(this.startDate);
            response.setEndDate(this.endDate);
            return response;
        }
    }
}