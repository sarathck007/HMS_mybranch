package com.group3.hotelsystem.mapper;


import org.springframework.stereotype.Component;

import com.group3.hotelsystem.dto.response.AdminResponse;
import com.group3.hotelsystem.model.Admin;

@Component
public class AdminMapper {
    
    public AdminResponse toResponse(Admin admin) {
        return AdminResponse.builder()
                .adminId(admin.getAdminId())
                .firstName(admin.getFirstName())
                .middleName(admin.getMiddleName())
                .lastName(admin.getLastName())
                .email(admin.getEmail())
                .phone(admin.getPhone())
                .createdAt(admin.getCreatedAt())  // Add this line
                .updatedAt(admin.getUpdatedAt())  // Add this line
                .build();
    }
}