package com.group3.hotelsystem.service;

import com.group3.hotelsystem.dto.equest.AdminRegistrationRequest;
import com.group3.hotelsystem.dto.equest.LoginRequest;
import com.group3.hotelsystem.dto.equest.ProfileUpdateRequest;
import com.group3.hotelsystem.dto.response.AdminResponse;
import com.group3.hotelsystem.dto.response.AuthResponse;

public interface AdminService {
    AuthResponse register(AdminRegistrationRequest request);
    AuthResponse login(LoginRequest request);
    AdminResponse updateProfile(String adminId, ProfileUpdateRequest request);
    void deleteAdmin(String adminId);
    
    // New methods
    AdminResponse findById(String adminId);
    AdminResponse findByEmail(String email);
}