package com.wk6.assignment4.service;

import com.wk6.assignment4.dto.request.LoginRequest;
import com.wk6.assignment4.dto.request.ProfileUpdateRequest;
import com.wk6.assignment4.dto.request.RegistrationRequest;
import com.wk6.assignment4.dto.response.AuthResponse;
import com.wk6.assignment4.dto.response.UserResponse;

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