package com.group3.hotelsystem.controller;

import com.group3.hotelsystem.dto.equest.LoginRequest;
import com.group3.hotelsystem.dto.equest.ProfileUpdateRequest;
import com.group3.hotelsystem.dto.equest.RegistrationRequest;
import com.group3.hotelsystem.dto.response.AdminResponse;
import com.group3.hotelsystem.dto.response.ApiResponse;
import com.group3.hotelsystem.dto.response.AuthResponse;
import com.group3.hotelsystem.dto.response.UserResponse;
import com.group3.hotelsystem.security.SecurityUtil;
import com.group3.hotelsystem.service.AdminService;
import com.group3.hotelsystem.service.BookingService;
import com.group3.hotelsystem.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
//@RequiredArgsConstructor
public class AuthController {
	private final UserService userService;
	private final SecurityUtil securityUtil;
	private final AdminService adminService;

	@Autowired
    public AuthController(UserService userService, SecurityUtil securityUtil, AdminService adminService) {
        this.userService = userService;
        this.securityUtil = securityUtil;
        this.adminService = adminService;
	}

	@PostMapping("/register")
	public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegistrationRequest request) {
		AuthResponse authResponse = userService.register(request);

		ApiResponse<AuthResponse> response = ApiResponse.<AuthResponse>builder().success(true)
				.message("User registered successfully").data(authResponse).build();

		return ResponseEntity.ok(response);
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
		AuthResponse authResponse = userService.login(request);

		ApiResponse<AuthResponse> response = ApiResponse.<AuthResponse>builder().success(true)
				.message("User logged in successfully").data(authResponse).build();

		return ResponseEntity.ok(response);
	}

	/*
	 * @PutMapping("/profile") public ResponseEntity<ApiResponse<UserResponse>>
	 * updateProfile(@Valid @RequestBody ProfileUpdateRequest request) {
	 * 
	 * String userId = securityUtil.getCurrentUserId(); UserResponse userResponse =
	 * userService.updateProfile(userId, request);
	 * 
	 * ApiResponse<UserResponse> response =
	 * ApiResponse.<UserResponse>builder().success(true)
	 * .message("Profile updated successfully").data(userResponse).build();
	 * 
	 * return ResponseEntity.ok(response); }
	 */
	
	@PutMapping("/profile")
	public ResponseEntity<ApiResponse<UserResponse>> updateProfile(@Valid @RequestBody ProfileUpdateRequest request) {
	    String userId = securityUtil.getCurrentUserId();
	    
	    // Check if the current user is an admin
	    if (securityUtil.isAdmin()) {
	        // Use AdminService for admin users
	        AdminResponse adminResponse = adminService.updateProfile(userId, request);
	        
	        // Convert AdminResponse to UserResponse for consistent API response
	        UserResponse userResponse = UserResponse.builder()
	                .userId(adminResponse.getAdminId())
	                .firstName(adminResponse.getFirstName())
	                .middleName(adminResponse.getMiddleName())
	                .lastName(adminResponse.getLastName())
	                .email(adminResponse.getEmail())
	                .phone(adminResponse.getPhone())
	                .createdAt(adminResponse.getCreatedAt())
	                .updatedAt(adminResponse.getUpdatedAt())
	                .build();
	        
	        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
	                .success(true)
	                .message("Profile updated successfully")
	                .data(userResponse)
	                .build();
	        
	        return ResponseEntity.ok(response);
	    } else {
	        // Use UserService for regular users
	        UserResponse userResponse = userService.updateProfile(userId, request);
	        
	        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
	                .success(true)
	                .message("Profile updated successfully")
	                .data(userResponse)
	                .build();
	        
	        return ResponseEntity.ok(response);
	    }
	}
}