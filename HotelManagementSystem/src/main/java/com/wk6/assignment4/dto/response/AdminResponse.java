package com.wk6.assignment4.dto.response;

public class AdminResponse {
    private String adminId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phone;
    private String department;

    // Default constructor
    public AdminResponse() {}

    // All-args constructor
    public AdminResponse(String adminId, String firstName, String middleName, String lastName, String email,
            String phone, String department) {
        this.adminId = adminId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.department = department;
    }

    // Static builder method
    public static AdminResponseBuilder builder() {
        return new AdminResponseBuilder();
    }

    // Getters and setters
    public String getAdminId() {
        return adminId;
    }
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
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
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    
    // Builder class
    public static class AdminResponseBuilder {
        private String adminId;
        private String firstName;
        private String middleName;
        private String lastName;
        private String email;
        private String phone;
        private String department;
        
        public AdminResponseBuilder adminId(String adminId) {
            this.adminId = adminId;
            return this;
        }
        
        public AdminResponseBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        
        public AdminResponseBuilder middleName(String middleName) {
            this.middleName = middleName;
            return this;
        }
        
        public AdminResponseBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        
        public AdminResponseBuilder email(String email) {
            this.email = email;
            return this;
        }
        
        public AdminResponseBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }
        
        public AdminResponseBuilder department(String department) {
            this.department = department;
            return this;
        }
        
        public AdminResponse build() {
            AdminResponse response = new AdminResponse();
            response.setAdminId(this.adminId);
            response.setFirstName(this.firstName);
            response.setMiddleName(this.middleName);
            response.setLastName(this.lastName);
            response.setEmail(this.email);
            response.setPhone(this.phone);
            response.setDepartment(this.department);
            return response;
        }
    }
}