package com.wk6.assignment4.dto.response;

public class UserResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    
    // Default constructor
    public UserResponse() {}
    
    // All-args constructor
    public UserResponse(String userId, String firstName, String lastName, String email, String phone) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }
    
    // Static builder method
    public static UserResponseBuilder builder() {
        return new UserResponseBuilder();
    }
    
    // Getters and setters
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    // Builder class
    public static class UserResponseBuilder {
        private String userId;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        
        public UserResponseBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }
        
        public UserResponseBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        
        public UserResponseBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        
        public UserResponseBuilder email(String email) {
            this.email = email;
            return this;
        }
        
        public UserResponseBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }
        
        public UserResponse build() {
            UserResponse response = new UserResponse();
            response.setUserId(this.userId);
            response.setFirstName(this.firstName);
            response.setLastName(this.lastName);
            response.setEmail(this.email);
            response.setPhone(this.phone);
            return response;
        }
    }
}