package com.wk6.assignment4.service;

import com.wk6.assignment4.dto.request.AdminRegistrationRequest;
import com.wk6.assignment4.dto.request.LoginRequest;
import com.wk6.assignment4.dto.request.ProfileUpdateRequest;
import com.wk6.assignment4.dto.response.AdminResponse;
import com.wk6.assignment4.dto.response.AuthResponse;

public interface AdminService {
    AuthResponse register(AdminRegistrationRequest request);
    AuthResponse login(LoginRequest request);
    AdminResponse updateProfile(String adminId, ProfileUpdateRequest request);
    void deleteAdmin(String adminId);
    
    // New methods
    AdminResponse findById(String adminId);
    AdminResponse findByEmail(String email);
}