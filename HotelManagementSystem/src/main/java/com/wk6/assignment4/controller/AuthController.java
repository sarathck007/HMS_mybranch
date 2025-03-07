package com.wk6.assignment4.controller;

import com.wk6.assignment4.dto.request.LoginRequest;
import com.wk6.assignment4.dto.request.ProfileUpdateRequest;
import com.wk6.assignment4.dto.request.RegistrationRequest;
import com.wk6.assignment4.dto.response.ApiResponse;
import com.wk6.assignment4.dto.response.AuthResponse;
import com.wk6.assignment4.dto.response.UserResponse;
import com.wk6.assignment4.security.SecurityUtil;
import com.wk6.assignment4.service.BookingService;
import com.wk6.assignment4.service.UserService;

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

	@Autowired
    public AuthController(UserService userService, SecurityUtil securityUtil) {
        this.userService = userService;
        this.securityUtil = securityUtil;
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

	@PutMapping("/profile")
	public ResponseEntity<ApiResponse<UserResponse>> updateProfile(@Valid @RequestBody ProfileUpdateRequest request) {

		String userId = securityUtil.getCurrentUserId();
		UserResponse userResponse = userService.updateProfile(userId, request);

		ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder().success(true)
				.message("Profile updated successfully").data(userResponse).build();

		return ResponseEntity.ok(response);
	}
}