package com.wk6.assignment4.model;

import java.util.ArrayList;
import java.util.List;

import com.wk6.assignment4.security.UserPrincipal;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "admins")
public class Admin extends BaseEntity implements UserPrincipal  {
    
    @Id
    @Column(name = "admin_id")
    private String adminId;

    @NotBlank
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @NotBlank
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

    @OneToMany(mappedBy = "admin")
    private List<Room> managedRooms = new ArrayList<>();

    // Static builder method
    public static AdminBuilder builder() {
        return new AdminBuilder();
    }
    
    public Admin(String adminId, @NotBlank String firstName, String middleName, @NotBlank String lastName,
            @Email String email, @Pattern(regexp = "^\\+?[1-9][0-9]{7,14}$") String phone, String password,
            List<Room> managedRooms) {
        super();
        this.adminId = adminId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.managedRooms = managedRooms;
    }

    public Admin() {
        
    }
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Room> getManagedRooms() {
        return managedRooms;
    }

    public void setManagedRooms(List<Room> managedRooms) {
        this.managedRooms = managedRooms;
    }
    
    @Override
    public String getRole() {
        return "ROLE_ADMIN";
    }
    
    // Builder class
    public static class AdminBuilder {
        private String adminId;
        private String firstName;
        private String middleName;
        private String lastName;
        private String email;
        private String phone;
        private String password;
        private List<Room> managedRooms = new ArrayList<>();
        
        public AdminBuilder adminId(String adminId) {
            this.adminId = adminId;
            return this;
        }
        
        public AdminBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        
        public AdminBuilder middleName(String middleName) {
            this.middleName = middleName;
            return this;
        }
        
        public AdminBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        
        public AdminBuilder email(String email) {
            this.email = email;
            return this;
        }
        
        public AdminBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }
        
        public AdminBuilder password(String password) {
            this.password = password;
            return this;
        }
        
        public AdminBuilder managedRooms(List<Room> managedRooms) {
            this.managedRooms = managedRooms;
            return this;
        }
        
        public Admin build() {
            Admin admin = new Admin();
            admin.setAdminId(this.adminId);
            admin.setFirstName(this.firstName);
            admin.setMiddleName(this.middleName);
            admin.setLastName(this.lastName);
            admin.setEmail(this.email);
            admin.setPhone(this.phone);
            admin.setPassword(this.password);
            admin.setManagedRooms(this.managedRooms);
            return admin;
        }
    }
}