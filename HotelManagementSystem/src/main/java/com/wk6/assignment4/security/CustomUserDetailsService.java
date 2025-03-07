package com.wk6.assignment4.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wk6.assignment4.model.Admin;
import com.wk6.assignment4.model.User;
import com.wk6.assignment4.repository.AdminRepository;
import com.wk6.assignment4.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	private final AdminRepository adminRepository;

	@Autowired
	public CustomUserDetailsService(UserRepository userRepository, AdminRepository adminRepository) {
		this.userRepository = userRepository;
		this.adminRepository = adminRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// First try to find a regular user
		User user = userRepository.findByEmail(email).orElse(null);
		if (user != null) {
			return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
					.password(user.getPassword())
					.authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))).build();
		}

		// If not found, try to find an admin
		Admin admin = adminRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

		return org.springframework.security.core.userdetails.User.withUsername(admin.getEmail())
				.password(admin.getPassword())
				.authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))).build();
	}
}