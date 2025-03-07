package com.wk6.assignment4.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.wk6.assignment4.model.BookingStatus;
import com.wk6.assignment4.security.SecurityUtil;
import com.wk6.assignment4.service.BookingService;
import com.wk6.assignment4.service.RoomService;
import com.wk6.assignment4.service.UserService;
import com.wk6.assignment4.dto.response.BookingResponse;
import com.wk6.assignment4.dto.response.RoomResponse;
import com.wk6.assignment4.exception.ResourceNotFoundException;

@Controller
public class WebController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private RoomService roomService;
    
    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private SecurityUtil securityUtil;
    
    // Public pages
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
    
    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }
    
    @GetMapping("/about")
    public String about() {
        return "about";
    }
    
    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
    
    // User pages
    @GetMapping("/user/dashboard")
    public String userDashboard(Model model, Authentication authentication) {
    	// Get current user
        String userId = securityUtil.getCurrentUserId();
        if (userId == null) {
            return "redirect:/login?error=unauthorized";
        }
        
        // Add user to model
        model.addAttribute("user", userService.findById(userId));
        
        List<BookingResponse> recentBookings = bookingService.findUserBookings(userId);
        
        // Get counts
        long activeBookingsCount = recentBookings.stream()
            .filter(b -> b.getStatus() == BookingStatus.CONFIRMED || b.getStatus() == BookingStatus.PENDING)
            .count();
        
        // Get upcoming booking
        BookingResponse upcomingBooking = recentBookings.stream()
            .filter(b -> b.getStatus() != BookingStatus.CANCELLED && b.getStatus() != BookingStatus.CHECKED_OUT)
            .filter(b -> b.getCheckInDate().isAfter(LocalDateTime.now()))
            .sorted((b1, b2) -> b1.getCheckInDate().compareTo(b2.getCheckInDate()))
            .findFirst()
            .orElse(null);
        
        model.addAttribute("activeBookingsCount", activeBookingsCount);
        model.addAttribute("totalBookingsCount", recentBookings.size());
        model.addAttribute("bookings", recentBookings);
        model.addAttribute("upcomingBooking", upcomingBooking);
        
        return "user/dashboard";
    }
    
    @GetMapping("/user/rooms")
    public String userRooms(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
            @RequestParam(required = false, defaultValue = "1") int adults,
            @RequestParam(required = false, defaultValue = "0") int children,
            Model model) {
        
        // Default to today and a week later if dates not provided
        if (checkIn == null) {
            checkIn = LocalDate.now();
        }
        if (checkOut == null) {
            checkOut = checkIn.plusDays(7);
        }
        
        List<RoomResponse> availableRooms = roomService.findAvailableRooms(checkIn, checkOut, adults, children);
        
        model.addAttribute("rooms", availableRooms);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        model.addAttribute("adults", adults);
        model.addAttribute("children", children);
        
        return "user/rooms";
    }
    
    @GetMapping("/user/booking/{roomId}")
    public String bookingForm(
            @PathVariable String roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
            @RequestParam int adults,
            @RequestParam int children,
            Model model) {
        
        RoomResponse room = roomService.findById(roomId);
        
        if (room == null) {
            return "redirect:/user/rooms";
        }
        
        // Calculate total nights and price
        long totalNights = java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut);
        double pricePerNight = room.getDiscountedPrice() != null ? 
            room.getDiscountedPrice().doubleValue() : 
            room.getActualPrice().doubleValue();
        double totalPrice = pricePerNight * totalNights;
        double taxesAndFees = totalPrice * 0.1; // 10% tax
        totalPrice += taxesAndFees;
        
        model.addAttribute("room", room);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        model.addAttribute("adults", adults);
        model.addAttribute("children", children);
        model.addAttribute("totalNights", totalNights);
        model.addAttribute("taxesAndFees", Math.round(taxesAndFees * 100.0) / 100.0);
        model.addAttribute("totalPrice", Math.round(totalPrice * 100.0) / 100.0);
        
        return "user/booking-form";
    }
    
    @GetMapping("/user/bookings")
    public String userBookings(Model model) {
        String userId = securityUtil.getCurrentUserId();
        
        List<BookingResponse> allBookings = bookingService.findUserBookings(userId);
        
        // Filter bookings by status
        List<BookingResponse> upcomingBookings = allBookings.stream()
            .filter(b -> b.getStatus() != BookingStatus.CANCELLED && b.getStatus() != BookingStatus.CHECKED_OUT)
            .filter(b -> b.getCheckInDate().isAfter(LocalDateTime.now()))
            .collect(Collectors.toList());
        
        List<BookingResponse> pastBookings = allBookings.stream()
            .filter(b -> b.getStatus() == BookingStatus.CHECKED_OUT || 
                   (b.getStatus() != BookingStatus.CANCELLED && 
                    b.getCheckOutDate().isBefore(LocalDateTime.now())))
            .collect(Collectors.toList());
        
        List<BookingResponse> cancelledBookings = allBookings.stream()
            .filter(b -> b.getStatus() == BookingStatus.CANCELLED)
            .collect(Collectors.toList());
        
        model.addAttribute("upcomingBookings", upcomingBookings);
        model.addAttribute("pastBookings", pastBookings);
        model.addAttribute("cancelledBookings", cancelledBookings);
        
        return "user/booking-history";
    }
    
    @GetMapping("/user/bookings/{bookingId}")
    public String bookingDetails(@PathVariable String bookingId, Model model) {
        try {
            // Get booking details
            BookingResponse booking = bookingService.findById(bookingId);
            
            // Check if booking belongs to current user
            String userId = securityUtil.getCurrentUserId();
            if (!booking.getUserId().equals(userId)) {
                return "redirect:/user/bookings";
            }
            
            model.addAttribute("booking", booking);
        } catch (ResourceNotFoundException e) {
            // Booking not found
            model.addAttribute("booking", null);
        }
        
        return "user/booking-details";
    }
    
    @GetMapping("/user/profile")
    public String userProfile(Model model) {
        String userId = securityUtil.getCurrentUserId();
        model.addAttribute("user", userService.findById(userId));
        return "user/profile";
    }
    
    // Admin pages
    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        // Get booking counts
        List<BookingResponse> allBookings = bookingService.findBookingsByStatus(null);
        
        long pendingBookings = allBookings.stream()
            .filter(b -> b.getStatus() == BookingStatus.PENDING)
            .count();
        
        long confirmedBookings = allBookings.stream()
            .filter(b -> b.getStatus() == BookingStatus.CONFIRMED)
            .count();
        
        long cancelledBookings = allBookings.stream()
            .filter(b -> b.getStatus() == BookingStatus.CANCELLED)
            .count();
        
        // Get monthly income data
        List<Map<String, Object>> monthlyIncome = bookingService.getMonthlyIncome();
        
        // Get booking status data for chart
        Map<String, Integer> bookingStatusData = new HashMap<>();
        bookingStatusData.put("confirmed", (int) confirmedBookings);
        bookingStatusData.put("checkedIn", (int) allBookings.stream()
            .filter(b -> b.getStatus() == BookingStatus.CHECKED_IN).count());
        bookingStatusData.put("pending", (int) pendingBookings);
        bookingStatusData.put("cancelled", (int) cancelledBookings);
        
        // Get recent bookings
        List<BookingResponse> recentBookings = allBookings.stream()
            .filter(b -> b.getStatus() == BookingStatus.PENDING)
            .limit(10)
            .collect(Collectors.toList());
        
        model.addAttribute("totalBookings", allBookings.size());
        model.addAttribute("pendingBookings", pendingBookings);
        model.addAttribute("activeRooms", roomService.count());
        model.addAttribute("monthlyIncome", monthlyIncome);
        model.addAttribute("bookingStatusData", bookingStatusData);
        model.addAttribute("recentBookings", recentBookings);
        
        return "admin/dashboard";
    }
    
    @GetMapping("/admin/bookings")
    public String adminBookings(Model model) {
        return getAdminBookingsByStatus(null, 1, model);
    }
    
    @GetMapping("/admin/bookings/status/{status}")
    public String adminBookingsByStatus(
            @PathVariable String status,
            @RequestParam(defaultValue = "1") int page,
            Model model) {
        
        try {
            BookingStatus bookingStatus = BookingStatus.valueOf(status.toUpperCase());
            return getAdminBookingsByStatus(bookingStatus, page, model);
        } catch (IllegalArgumentException e) {
            // Invalid status, redirect to all bookings
            return "redirect:/admin/bookings";
        }
    }
    
    private String getAdminBookingsByStatus(BookingStatus status, int page, Model model) {
        // Get bookings by status (or all if status is null)
        List<BookingResponse> bookings = bookingService.findBookingsByStatus(status);
        
        // Get counts for filter cards
        long totalBookings = bookingService.findBookingsByStatus(null).size();
        long pendingCount = bookingService.findBookingsByStatus(BookingStatus.PENDING).size();
        long confirmedCount = bookingService.findBookingsByStatus(BookingStatus.CONFIRMED).size();
        long cancelledCount = bookingService.findBookingsByStatus(BookingStatus.CANCELLED).size();
        
        model.addAttribute("bookings", bookings);
        model.addAttribute("totalBookings", totalBookings);
        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("confirmedCount", confirmedCount);
        model.addAttribute("cancelledCount", cancelledCount);
        model.addAttribute("currentStatus", status != null ? status.name().toLowerCase() : null);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", 1);  // Placeholder for pagination
        model.addAttribute("currentUrl", status != null ? "/admin/bookings/status/" + status.name().toLowerCase() : "/admin/bookings");
        
        return "admin/bookings-management";
    }
    
    @GetMapping("/admin/rooms")
    public String adminRooms(Model model) {
        List<RoomResponse> rooms = roomService.findAll();
        model.addAttribute("rooms", rooms);
        return "admin/rooms-management";
    }
    
    @GetMapping("/admin/reports")
    public String adminReports(Model model) {
        // Get monthly income data
        List<Map<String, Object>> monthlyIncome = bookingService.getMonthlyIncome();
        model.addAttribute("monthlyIncome", monthlyIncome);
        return "admin/reports";
    }
    
    @GetMapping("/admin/profile")
    public String adminProfile(Model model) {
        String adminId = securityUtil.getCurrentUserId();
        model.addAttribute("user", userService.findById(adminId));
        return "admin/profile";
    }
}