package com.wk6.assignment4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wk6.assignment4.dto.request.BookingRequest;
import com.wk6.assignment4.dto.response.BookingResponse;
import com.wk6.assignment4.exception.ResourceNotFoundException;
import com.wk6.assignment4.exception.RoomNotAvailableException;
import com.wk6.assignment4.mapper.BookingMapper;
import com.wk6.assignment4.model.Booking;
import com.wk6.assignment4.model.BookingStatus;
import com.wk6.assignment4.model.Room;
import com.wk6.assignment4.model.User;
import com.wk6.assignment4.repository.BookingRepository;
import com.wk6.assignment4.repository.RoomRepository;
import com.wk6.assignment4.repository.UserRepository;
import com.wk6.assignment4.service.BookingService;
import com.wk6.assignment4.util.Constants;
import com.wk6.assignment4.util.DateUtil;
import com.wk6.assignment4.util.PriceUtil;
import com.wk6.assignment4.util.StringUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final BookingMapper bookingMapper;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, UserRepository userRepository,
            RoomRepository roomRepository, BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.bookingMapper = bookingMapper;
    }

    @Override
    public BookingResponse createBooking(BookingRequest request, String userId) {
        // Validate dates
        if (!DateUtil.isDateRangeValid(request.getCheckInDate(), request.getCheckOutDate())) {
            throw new IllegalArgumentException(Constants.INVALID_DATE_RANGE);
        }

        // Check room availability
        if (!isRoomAvailable(request.getRoomId(), request.getCheckInDate(), request.getCheckOutDate())) {
            throw new RoomNotAvailableException(Constants.ROOM_NOT_AVAILABLE);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.USER_NOT_FOUND));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ROOM_NOT_FOUND));

        // Validate guest count
        if (request.getGuests() > room.getMaxAdults()) {
            throw new IllegalArgumentException(Constants.INVALID_GUEST_COUNT);
        }

        // Calculate total price
        long nights = DateUtil.calculateNights(request.getCheckInDate(), request.getCheckOutDate());
        BigDecimal totalPrice = PriceUtil.calculateTotalPrice(
                room.getDiscountedPrice() != null ? room.getDiscountedPrice() : room.getActualPrice(), nights);

        Booking booking = Booking.builder().bookingId(StringUtil.generateUniqueId(Constants.BOOKING_ID_PREFIX))
                .user(user).room(room).checkInDate(request.getCheckInDate()).checkOutDate(request.getCheckOutDate())
                .totalPrice(totalPrice).guests(request.getGuests()).status(BookingStatus.PENDING).build();

        return bookingMapper.toResponse(bookingRepository.save(booking));
    }

    @Override
    public BookingResponse updateBooking(String bookingId, BookingRequest request) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.BOOKING_NOT_FOUND));

        // Check if the booking can be modified
        if (booking.getStatus() == BookingStatus.CANCELLED || booking.getCheckInDate().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Booking cannot be modified");
        }

        if (!DateUtil.isDateRangeValid(request.getCheckInDate(), request.getCheckOutDate())) {
            throw new IllegalArgumentException(Constants.INVALID_DATE_RANGE);
        }

        // If room is being changed, check availability
        if (!booking.getRoom().getRoomId().equals(request.getRoomId())) {
            if (!isRoomAvailable(request.getRoomId(), request.getCheckInDate(), request.getCheckOutDate())) {
                throw new RoomNotAvailableException(Constants.ROOM_NOT_AVAILABLE);
            }
            Room newRoom = roomRepository.findById(request.getRoomId())
                    .orElseThrow(() -> new ResourceNotFoundException(Constants.ROOM_NOT_FOUND));
            booking.setRoom(newRoom);
        }

        bookingMapper.updateEntity(booking, request);

        // Recalculate total price
        long nights = DateUtil.calculateNights(request.getCheckInDate(), request.getCheckOutDate());
        BigDecimal totalPrice = PriceUtil.calculateTotalPrice(
                booking.getRoom().getDiscountedPrice() != null ? booking.getRoom().getDiscountedPrice()
                        : booking.getRoom().getActualPrice(),
                nights);
        booking.setTotalPrice(totalPrice);

        return bookingMapper.toResponse(bookingRepository.save(booking));
    }

    @Override
    public void cancelBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.BOOKING_NOT_FOUND));

        // Check if booking can be cancelled
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalStateException("Booking is already cancelled");
        }
        if (booking.getCheckInDate().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Past bookings cannot be cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    @Override
    public List<BookingResponse> findUserBookings(String userId) {
        return bookingRepository.findByUser_UserId(userId).stream().map(bookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> findBookingsByStatus(BookingStatus status) {
        if (status == null) {
            return bookingRepository.findAll().stream().map(bookingMapper::toResponse)
                    .collect(Collectors.toList());
        }
        return bookingRepository.findByStatus(status).stream().map(bookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getMonthlyIncome() {
        return bookingRepository.getMonthlyIncome();
    }

    @Override
    public boolean isRoomAvailable(String roomId, LocalDateTime checkIn, LocalDateTime checkOut) {
        return bookingRepository.countOverlappingBookings(roomId, checkIn, checkOut) == 0;
    }
    
    @Override
    public BookingResponse findById(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.BOOKING_NOT_FOUND));
        return bookingMapper.toResponse(booking);
    }
    
    @Override
    public BookingResponse updateBookingStatus(String bookingId, BookingStatus status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.BOOKING_NOT_FOUND));
        
        // Validate status transition
        validateStatusTransition(booking.getStatus(), status);
        
        booking.setStatus(status);
        return bookingMapper.toResponse(bookingRepository.save(booking));
    }
    
    // Helper method to validate booking status transitions
    private void validateStatusTransition(BookingStatus currentStatus, BookingStatus newStatus) {
        // Implement rules for valid status transitions
        if (currentStatus == BookingStatus.CANCELLED || currentStatus == BookingStatus.CHECKED_OUT) {
            throw new IllegalStateException("Cannot change status of cancelled or checked out bookings");
        }
        
        if (currentStatus == BookingStatus.PENDING && 
            (newStatus == BookingStatus.CHECKED_IN || newStatus == BookingStatus.CHECKED_OUT)) {
            throw new IllegalStateException("Booking must be confirmed before check-in/check-out");
        }
        
        if (currentStatus == BookingStatus.CONFIRMED && newStatus == BookingStatus.CHECKED_OUT) {
            throw new IllegalStateException("Booking must be checked in before check-out");
        }
    }
}