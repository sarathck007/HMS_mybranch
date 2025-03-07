package com.wk6.assignment4.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "queries")
public class Query extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    // Static builder method
    public static QueryBuilder builder() {
        return new QueryBuilder();
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
    
    // Builder class
    public static class QueryBuilder {
        private Long id;
        private User user;
        private String name;
        private String email;
        private String message;
        
        public QueryBuilder id(Long id) {
            this.id = id;
            return this;
        }
        
        public QueryBuilder user(User user) {
            this.user = user;
            return this;
        }
        
        public QueryBuilder name(String name) {
            this.name = name;
            return this;
        }
        
        public QueryBuilder email(String email) {
            this.email = email;
            return this;
        }
        
        public QueryBuilder message(String message) {
            this.message = message;
            return this;
        }
        
        public Query build() {
            Query query = new Query();
            query.setId(this.id);
            query.setUser(this.user);
            query.setName(this.name);
            query.setEmail(this.email);
            query.setMessage(this.message);
            return query;
        }
    }
}