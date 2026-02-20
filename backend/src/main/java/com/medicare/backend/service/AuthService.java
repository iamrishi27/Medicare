package com.medicare.backend.service;

import com.medicare.backend.dto.LoginRequest;
import com.medicare.backend.dto.RegisterRequest;
import com.medicare.backend.model.Role;
import com.medicare.backend.model.User;
import com.medicare.backend.repository.RoleRepository;
import com.medicare.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role role = roleRepository.findByName("ROLE_PATIENT")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRoles(Collections.singleton(role));

        userRepository.save(user);
    }

    public String authenticateUser(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Later replace with real JWT
        return "JWT-TOKEN-GENERATED";
    }
}
