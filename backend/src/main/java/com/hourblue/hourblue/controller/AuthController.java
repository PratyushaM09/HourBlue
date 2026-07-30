package com.hourblue.hourblue.controller;

import com.hourblue.hourblue.dto.LoginRequest;
import com.hourblue.hourblue.dto.LoginResponse;
import com.hourblue.hourblue.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

/**
 * Single admin login (Section 5.2 / 9.1). No registration endpoint exists -
 * the one admin_user row is created via a Flyway seed migration or a manual DB insert.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).build();
        }

        String token = jwtService.generateToken(request.username());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
