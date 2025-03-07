package com.wk6.assignment4.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wk6.assignment4.dto.request.AdminRegistrationRequest;
import com.wk6.assignment4.dto.request.LoginRequest;
import com.wk6.assignment4.dto.request.ProfileUpdateRequest;
import com.wk6.assignment4.dto.response.AdminResponse;
import com.wk6.assignment4.dto.response.AuthResponse;
import com.wk6.assignment4.dto.response.UserResponse;
import com.wk6.assignment4.exception.EmailAlreadyExistsException;
import com.wk6.assignment4.exception.ResourceNotFoundException;
import com.wk6.assignment4.mapper.AdminMapper;
import com.wk6.assignment4.model.Admin;
import com.wk6.assignment4.repository.AdminRepository;
import com.wk6.assignment4.security.JwtUtil;
import com.wk6.assignment4.service.AdminService;
import com.wk6.assignment4.util.Constants;
import com.wk6.assignment4.util.StringUtil;
import com.wk6.assignment4.util.ValidationUtil;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AdminServiceImpl(
            AdminRepository adminRepository,
            AdminMapper adminMapper,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil,
            AuthenticationManager authenticationManager) {
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponse register(AdminRegistrationRequest request) {
        // Validate input
        if (!ValidationUtil.isValidEmail(request.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!ValidationUtil.isValidPhone(request.getPhone())) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
        if (!ValidationUtil.isValidPassword(request.getPassword())) {
            throw new IllegalArgumentException("Password does not meet security requirements");
        }

        // Check if email already exists
        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(Constants.EMAIL_EXISTS);
        }

        // Create new admin
        Admin admin = Admin.builder()
                .adminId(StringUtil.generateUniqueId(Constants.ADMIN_ID_PREFIX))
                .firstName(StringUtil.sanitizeString(request.getFirstName()))
                .middleName(StringUtil.sanitizeString(request.getMiddleName()))
                .lastName(StringUtil.sanitizeString(request.getLastName()))
                .email(request.getEmail().toLowerCase())
                .phone(StringUtil.formatPhoneNumber(request.getPhone()))
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        // Save admin and generate token
        Admin savedAdmin = adminRepository.save(admin);
        String token = jwtUtil.generateToken(savedAdmin);

        // Convert AdminResponse to UserResponse for AuthResponse
        AdminResponse adminResponse = adminMapper.toResponse(savedAdmin);
        UserResponse userResponse = convertToUserResponse(adminResponse);

        // Return response
        return AuthResponse.builder()
                .token(token)
                .role("ADMIN")
                .user(userResponse)
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        // Authenticate admin
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        // Get admin details
        Admin admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ADMIN_NOT_FOUND));

        // Generate token
        String token = jwtUtil.generateToken(admin);

        // Convert AdminResponse to UserResponse for AuthResponse
        AdminResponse adminResponse = adminMapper.toResponse(admin);
        UserResponse userResponse = convertToUserResponse(adminResponse);

        // Return response
        return AuthResponse.builder()
                .token(token)
                .role("ADMIN")
                .user(userResponse)
                .build();
    }

    @Override
    public AdminResponse updateProfile(String adminId, ProfileUpdateRequest request) {
        // Validate input
        if (!ValidationUtil.isValidEmail(request.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!ValidationUtil.isValidPhone(request.getPhone())) {
            throw new IllegalArgumentException("Invalid phone number format");
        }

        // Get admin
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ADMIN_NOT_FOUND));

        // Check email uniqueness
        if (!admin.getEmail().equals(request.getEmail()) && 
            adminRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(Constants.EMAIL_EXISTS);
        }

        // Update admin details
        admin.setFirstName(StringUtil.sanitizeString(request.getFirstName()));
        admin.setMiddleName(StringUtil.sanitizeString(request.getMiddleName()));
        admin.setLastName(StringUtil.sanitizeString(request.getLastName()));
        admin.setEmail(request.getEmail().toLowerCase());
        admin.setPhone(StringUtil.formatPhoneNumber(request.getPhone()));

        // Save and return
        return adminMapper.toResponse(adminRepository.save(admin));
    }

    @Override
    public void deleteAdmin(String adminId) {
        if (!adminRepository.existsById(adminId)) {
            throw new ResourceNotFoundException(Constants.ADMIN_NOT_FOUND);
        }
        adminRepository.deleteById(adminId);
    }
    
    @Override
    public AdminResponse findById(String adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ADMIN_NOT_FOUND));
        return adminMapper.toResponse(admin);
    }
    
    @Override
    public AdminResponse findByEmail(String email) {
        Admin admin = adminRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ADMIN_NOT_FOUND));
        return adminMapper.toResponse(admin);
    }
    
    // Helper method to convert AdminResponse to UserResponse
    private UserResponse convertToUserResponse(AdminResponse adminResponse) {
        return UserResponse.builder()
                .userId(adminResponse.getAdminId())
                .firstName(adminResponse.getFirstName())
                .lastName(adminResponse.getLastName())
                .email(adminResponse.getEmail())
                .phone(adminResponse.getPhone())
                .build();
    }
}