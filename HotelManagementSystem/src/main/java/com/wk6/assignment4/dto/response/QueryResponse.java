package com.wk6.assignment4.dto.response;

import java.time.LocalDateTime;

public class QueryResponse {
    private Long id;
    private String name;
    private String email;
    private String message;
    private LocalDateTime createdAt;
    private String status;

    // Default constructor
    public QueryResponse() {}

    // All-args constructor
    public QueryResponse(Long id, String name, String email, String message, LocalDateTime createdAt, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.message = message;
        this.createdAt = createdAt;
        this.status = status;
    }

    // Static builder method
    public static QueryResponseBuilder builder() {
        return new QueryResponseBuilder();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Builder class
    public static class QueryResponseBuilder {
        private Long id;
        private String name;
        private String email;
        private String message;
        private LocalDateTime createdAt;
        private String status;
        
        public QueryResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }
        
        public QueryResponseBuilder name(String name) {
            this.name = name;
            return this;
        }
        
        public QueryResponseBuilder email(String email) {
            this.email = email;
            return this;
        }
        
        public QueryResponseBuilder message(String message) {
            this.message = message;
            return this;
        }
        
        public QueryResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        
        public QueryResponseBuilder status(String status) {
            this.status = status;
            return this;
        }
        
        public QueryResponse build() {
            QueryResponse response = new QueryResponse();
            response.setId(this.id);
            response.setName(this.name);
            response.setEmail(this.email);
            response.setMessage(this.message);
            response.setCreatedAt(this.createdAt);
            response.setStatus(this.status);
            return response;
        }
    }
}