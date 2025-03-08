package com.group3.hotelsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

import com.group3.hotelsystem.security.UserPrincipal;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserPrincipal  {

    @Id
    @Column(name = "user_id")
    private String userId;

    @NotBlank
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    //@Pattern(regexp = "^\\+?[1-9][0-9]{7,14}$")
    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Query> queries = new ArrayList<>();

    // Static builder method
    public static UserBuilder builder() {
        return new UserBuilder();
    }
    
    public User(String userId, @NotBlank String firstName, String lastName, @Email String email,
            @Pattern(regexp = "^\\+?[1-9][0-9]{7,14}$") String phone, String password, List<Booking> bookings,
            List<Query> queries) {
        super();
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.bookings = bookings;
        this.queries = queries;
    }
    
    public User() {}
    
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Query> getQueries() {
        return queries;
    }

    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }
    
    @Override
    public String getRole() {
        return "ROLE_USER";
    }
    
    // Builder class
    public static class UserBuilder {
        private String userId;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String password;
        private List<Booking> bookings = new ArrayList<>();
        private List<Query> queries = new ArrayList<>();
        
        public UserBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }
        
        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        
        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        
        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }
        
        public UserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }
        
        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }
        
        public UserBuilder bookings(List<Booking> bookings) {
            this.bookings = bookings;
            return this;
        }
        
        public UserBuilder queries(List<Query> queries) {
            this.queries = queries;
            return this;
        }
        
        public User build() {
            User user = new User();
            user.setUserId(this.userId);
            user.setFirstName(this.firstName);
            user.setLastName(this.lastName);
            user.setEmail(this.email);
            user.setPhone(this.phone);
            user.setPassword(this.password);
            user.setBookings(this.bookings);
            user.setQueries(this.queries);
            return user;
        }
    }
}