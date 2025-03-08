package com.group3.hotelsystem.service;

import com.group3.hotelsystem.dto.equest.LoginRequest;
import com.group3.hotelsystem.dto.equest.ProfileUpdateRequest;
import com.group3.hotelsystem.dto.equest.RegistrationRequest;
import com.group3.hotelsystem.dto.response.AuthResponse;
import com.group3.hotelsystem.dto.response.UserResponse;

public interface UserService {
    AuthResponse register(RegistrationRequest request);
    AuthResponse login(LoginRequest request);
    UserResponse updateProfile(String userId, ProfileUpdateRequest request);
    void deleteUser(String userId);
    boolean existsByEmail(String email);
    
    // New methods
    UserResponse findById(String userId);
    UserResponse findByEmail(String email);
}