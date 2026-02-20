package com.medicare.backend.controller;

import com.medicare.backend.dto.LoginRequest;
import com.medicare.backend.dto.RegisterRequest;
import com.medicare.backend.security.JwtTokenProvider;
import com.medicare.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        authService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        authService.authenticateUser(request);
        String token = jwtTokenProvider.generateToken(request.getUsername());
        return ResponseEntity.ok(token);
    }
}