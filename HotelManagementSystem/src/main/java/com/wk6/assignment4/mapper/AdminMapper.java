package com.wk6.assignment4.mapper;


import org.springframework.stereotype.Component;

import com.wk6.assignment4.dto.response.AdminResponse;
import com.wk6.assignment4.model.Admin;

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
                .build();
    }
}