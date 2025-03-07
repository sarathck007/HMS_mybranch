package com.wk6.assignment4.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity {
    
    @Id
    @Column(name = "booking_id")
    private String bookingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Future
    @Column(name = "check_in_date", nullable = false)
    private LocalDateTime checkInDate;

    @Future
    @Column(name = "check_out_date", nullable = false)
    private LocalDateTime checkOutDate;

    @DecimalMin("0.0")
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Min(1)
    @Column(nullable = false)
    private int guests;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;
    
    // Static builder method
    public static BookingBuilder builder() {
        return new BookingBuilder();
    }
    
    public Booking(String bookingId, User user, Room room, @Future LocalDateTime checkInDate,
            @Future LocalDateTime checkOutDate, @DecimalMin("0.0") BigDecimal totalPrice, @Min(1) int guests,
            BookingStatus status) {
        super();
        this.bookingId = bookingId;
        this.user = user;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
        this.guests = guests;
        this.status = status;
    }
    
    public Booking() {}

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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
    
    // Builder class
    public static class BookingBuilder {
        private String bookingId;
        private User user;
        private Room room;
        private LocalDateTime checkInDate;
        private LocalDateTime checkOutDate;
        private BigDecimal totalPrice;
        private int guests;
        private BookingStatus status;
        
        public BookingBuilder bookingId(String bookingId) {
            this.bookingId = bookingId;
            return this;
        }
        
        public BookingBuilder user(User user) {
            this.user = user;
            return this;
        }
        
        public BookingBuilder room(Room room) {
            this.room = room;
            
            return this;
        }
        
        public BookingBuilder checkInDate(LocalDateTime checkInDate) {
            this.checkInDate = checkInDate;
            return this;
        }
        
        public BookingBuilder checkOutDate(LocalDateTime checkOutDate) {
            this.checkOutDate = checkOutDate;
            return this;
        }
        
        public BookingBuilder totalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }
        
        public BookingBuilder guests(int guests) {
            this.guests = guests;
            return this;
        }
        
        public BookingBuilder status(BookingStatus status) {
            this.status = status;
            return this;
        }
        
        public Booking build() {
            Booking booking = new Booking();
            booking.setBookingId(this.bookingId);
            booking.setUser(this.user);
            booking.setRoom(this.room);
            booking.setCheckInDate(this.checkInDate);
            booking.setCheckOutDate(this.checkOutDate);
            booking.setTotalPrice(this.totalPrice);
            booking.setGuests(this.guests);
            booking.setStatus(this.status);
            return booking;
        }
    }
}