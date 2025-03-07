package com.wk6.assignment4.service.impl;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wk6.assignment4.dto.request.LoginRequest;
import com.wk6.assignment4.dto.request.ProfileUpdateRequest;
import com.wk6.assignment4.dto.request.RegistrationRequest;
import com.wk6.assignment4.dto.response.AuthResponse;
import com.wk6.assignment4.dto.response.UserResponse;
import com.wk6.assignment4.exception.EmailAlreadyExistsException;
import com.wk6.assignment4.exception.ResourceNotFoundException;
import com.wk6.assignment4.mapper.UserMapper;
import com.wk6.assignment4.model.User;
import com.wk6.assignment4.repository.UserRepository;
import com.wk6.assignment4.security.JwtUtil;
import com.wk6.assignment4.service.UserService;
import com.wk6.assignment4.util.Constants;
import com.wk6.assignment4.util.StringUtil;
import com.wk6.assignment4.util.ValidationUtil;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponse register(RegistrationRequest request) {
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
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(Constants.EMAIL_EXISTS);
        }

        // Create new user
        User user = User.builder()
                .userId(StringUtil.generateUniqueId(Constants.USER_ID_PREFIX))
                .firstName(StringUtil.sanitizeString(request.getFirstName()))
                .lastName(StringUtil.sanitizeString(request.getLastName()))
                .email(request.getEmail().toLowerCase())
                .phone(StringUtil.formatPhoneNumber(request.getPhone()))
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        System.out.println("User before save: " + user.getFirstName() + ", " + user.getEmail() + ", " + user.getPhone());

        // Save user and generate token
        User savedUser = userRepository.save(user);
        String token = jwtUtil.generateToken(savedUser);

        // Return response
        return AuthResponse.builder()
                .token(token)
                .role("USER")
                .user(userMapper.toResponse(savedUser))
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        // Authenticate user
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        // Get user details
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(Constants.USER_NOT_FOUND));

        // Generate token
        String token = jwtUtil.generateToken(user);

        // Return response
        return AuthResponse.builder()
                .token(token)
                .role("USER")
                .user(userMapper.toResponse(user))
                .build();
    }

    @Override
    @Transactional
    public UserResponse updateProfile(String userId, ProfileUpdateRequest request) {
        // Validate input
        if (!ValidationUtil.isValidEmail(request.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!ValidationUtil.isValidPhone(request.getPhone())) {
            throw new IllegalArgumentException("Invalid phone number format");
        }

        // Get user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.USER_NOT_FOUND));

        // Check email uniqueness
        if (!user.getEmail().equals(request.getEmail()) && 
            userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(Constants.EMAIL_EXISTS);
        }

        // Update user details
        user.setFirstName(StringUtil.sanitizeString(request.getFirstName()));
        user.setLastName(StringUtil.sanitizeString(request.getLastName()));
        user.setEmail(request.getEmail().toLowerCase());
        user.setPhone(StringUtil.formatPhoneNumber(request.getPhone()));

        // Save and return
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(Constants.USER_NOT_FOUND);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email.toLowerCase());
    }
    
    @Override
    public UserResponse findById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.USER_NOT_FOUND));
        return userMapper.toResponse(user);
    }
    
    @Override
    public UserResponse findByEmail(String email) {
        User user = userRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException(Constants.USER_NOT_FOUND));
        return userMapper.toResponse(user);
    }
}