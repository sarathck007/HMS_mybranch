package com.group3.hotelsystem.dto.response;

public class AuthResponse {
    private String token;
    private String role;
    private UserResponse user;

    // Default constructor
    public AuthResponse() {}

    // All-args constructor
    public AuthResponse(String token, String role, UserResponse user) {
        this.token = token;
        this.role = role;
        this.user = user;
    }

    // Static builder method
    public static AuthResponseBuilder builder() {
        return new AuthResponseBuilder();
    }

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    // Builder class
    public static class AuthResponseBuilder {
        private String token;
        private String role;
        private UserResponse user;
        
        public AuthResponseBuilder token(String token) {
            this.token = token;
            return this;
        }
        
        public AuthResponseBuilder role(String role) {
            this.role = role;
            return this;
        }
        
        public AuthResponseBuilder user(UserResponse user) {
            this.user = user;
            return this;
        }
        
        public AuthResponse build() {
            AuthResponse response = new AuthResponse();
            response.setToken(this.token);
            response.setRole(this.role);
            response.setUser(this.user);
            return response;
        }
    }
}