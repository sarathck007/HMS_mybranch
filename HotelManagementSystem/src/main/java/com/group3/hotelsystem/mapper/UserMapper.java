package com.group3.hotelsystem.mapper;

import org.springframework.stereotype.Component;

import com.group3.hotelsystem.dto.response.UserResponse;
import com.group3.hotelsystem.model.User;

@Component
public class UserMapper {
    
    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .middleName(null)  // Add this line, even though User doesn't have middleName
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .createdAt(user.getCreatedAt())  // Add this line
                .updatedAt(user.getUpdatedAt())  // Add this line
                .build();
    }
}