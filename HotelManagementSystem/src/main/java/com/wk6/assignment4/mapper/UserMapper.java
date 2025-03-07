package com.wk6.assignment4.mapper;

import org.springframework.stereotype.Component;

import com.wk6.assignment4.dto.response.UserResponse;
import com.wk6.assignment4.model.User;

@Component
public class UserMapper {
    
    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }
}