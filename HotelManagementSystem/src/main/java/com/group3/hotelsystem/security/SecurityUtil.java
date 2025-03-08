package com.group3.hotelsystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.group3.hotelsystem.repository.AdminRepository;
import com.group3.hotelsystem.repository.UserRepository;

@Component
public class SecurityUtil {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AdminRepository adminRepository;

    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return null;
    }
    
    public String getCurrentUserId() {
        String email = getCurrentUserEmail();
        if (email == null) {
            return null;
        }
        
        // First try to find a user
        return userRepository.findByEmail(email)
                .map(user -> user.getUserId())
                .orElseGet(() -> adminRepository.findByEmail(email)
                        .map(admin -> admin.getAdminId())
                        .orElse(null));
    }

    public boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && 
               authentication.getAuthorities().stream()
                   .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}