package com.group3.hotelsystem.controller;

import com.group3.hotelsystem.dto.equest.AdminRegistrationRequest;
import com.group3.hotelsystem.dto.equest.LoginRequest;
import com.group3.hotelsystem.dto.equest.ProfileUpdateRequest;
import com.group3.hotelsystem.dto.response.AdminResponse;
import com.group3.hotelsystem.dto.response.ApiResponse;
import com.group3.hotelsystem.dto.response.AuthResponse;
import com.group3.hotelsystem.security.SecurityUtil;
import com.group3.hotelsystem.service.AdminService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/admin")
//@RequiredArgsConstructor
public class AdminAuthController {
	private final AdminService adminService;
	private final SecurityUtil securityUtil;

	@Autowired
	public AdminAuthController(AdminService adminService, SecurityUtil securityUtil) {
		this.adminService = adminService;
		this.securityUtil = securityUtil;
	}

	@PostMapping("/register")
	public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody AdminRegistrationRequest request) {
		AuthResponse authResponse = adminService.register(request);

		ApiResponse<AuthResponse> response = ApiResponse.<AuthResponse>builder().success(true)
				.message("Admin registered successfully").data(authResponse).build();

		return ResponseEntity.ok(response);
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
		AuthResponse authResponse = adminService.login(request);

		ApiResponse<AuthResponse> response = ApiResponse.<AuthResponse>builder().success(true)
				.message("Admin logged in successfully").data(authResponse).build();

		return ResponseEntity.ok(response);
	}

	@PutMapping("/profile")
	public ResponseEntity<ApiResponse<AdminResponse>> updateProfile(@Valid @RequestBody ProfileUpdateRequest request) {

		String adminId = securityUtil.getCurrentUserId();
		AdminResponse adminResponse = adminService.updateProfile(adminId, request);

		ApiResponse<AdminResponse> response = ApiResponse.<AdminResponse>builder().success(true)
				.message("Profile updated successfully").data(adminResponse).build();

		return ResponseEntity.ok(response);
	}
}