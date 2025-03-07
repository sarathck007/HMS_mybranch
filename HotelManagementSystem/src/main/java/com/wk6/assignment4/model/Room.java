package com.wk6.assignment4.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room extends BaseEntity {
    
    @Id
    @Column(name = "room_id")
    private String roomId;

    @NotBlank
    @Column(name = "room_name", nullable = false)
    private String roomName;

    @DecimalMin("0.0")
    @Column(name = "actual_price", nullable = false)
    private BigDecimal actualPrice;

    @DecimalMin("0.0")
    @Column(name = "discounted_price")
    private BigDecimal discountedPrice;

    @Column(columnDefinition = "TEXT")
    private String features;

    @Future
    @Column(name = "start_date")
    private LocalDate startDate;

    @Future
    @Column(name = "end_date")
    private LocalDate endDate;

    @Min(1)
    @Column(name = "max_adults")
    private int maxAdults;

    @Min(0)
    @Column(name = "max_children")
    private int maxChildren;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    // Static builder method
    public static RoomBuilder builder() {
        return new RoomBuilder();
    }
    
    public Room() {}
    
    public Room(String roomId, @NotBlank String roomName, @DecimalMin("0.0") BigDecimal actualPrice,
            @DecimalMin("0.0") BigDecimal discountedPrice, String features, @Future LocalDate startDate,
            @Future LocalDate endDate, @Min(1) int maxAdults, @Min(0) int maxChildren, Admin admin,
            List<Booking> bookings) {
        super();
        this.roomId = roomId;
        this.roomName = roomName;
        this.actualPrice = actualPrice;
        this.discountedPrice = discountedPrice;
        this.features = features;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxAdults = maxAdults;
        this.maxChildren = maxChildren;
        this.admin = admin;
        this.bookings = bookings;
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

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
    
    // Builder class
    public static class RoomBuilder {
        private String roomId;
        private String roomName;
        private BigDecimal actualPrice;
        private BigDecimal discountedPrice;
        private String features;
        private LocalDate startDate;
        private LocalDate endDate;
        private int maxAdults;
        private int maxChildren;
        private Admin admin;
        private List<Booking> bookings = new ArrayList<>();
        
        public RoomBuilder roomId(String roomId) {
            this.roomId = roomId;
            return this;
        }
        
        public RoomBuilder roomName(String roomName) {
            this.roomName = roomName;
            return this;
        }
        
        public RoomBuilder actualPrice(BigDecimal actualPrice) {
            this.actualPrice = actualPrice;
            return this;
        }
        
        public RoomBuilder discountedPrice(BigDecimal discountedPrice) {
            this.discountedPrice = discountedPrice;
            return this;
        }
        
        public RoomBuilder features(String features) {
            this.features = features;
            return this;
        }
        
        public RoomBuilder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }
        
        public RoomBuilder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }
        
        public RoomBuilder maxAdults(int maxAdults) {
            this.maxAdults = maxAdults;
            return this;
        }
        
        public RoomBuilder maxChildren(int maxChildren) {
            this.maxChildren = maxChildren;
            return this;
        }
        
        public RoomBuilder admin(Admin admin) {
            this.admin = admin;
            return this;
        }
        
        public RoomBuilder bookings(List<Booking> bookings) {
            this.bookings = bookings;
            return this;
        }
        
        public Room build() {
            Room room = new Room();
            room.setRoomId(this.roomId);
            room.setRoomName(this.roomName);
            room.setActualPrice(this.actualPrice);
            room.setDiscountedPrice(this.discountedPrice);
            room.setFeatures(this.features);
            room.setStartDate(this.startDate);
            room.setEndDate(this.endDate);
            room.setMaxAdults(this.maxAdults);
            room.setMaxChildren(this.maxChildren);
            room.setAdmin(this.admin);
            room.setBookings(this.bookings);
            return room;
        }
    }
}