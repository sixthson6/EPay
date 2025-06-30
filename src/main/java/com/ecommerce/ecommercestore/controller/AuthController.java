package com.ecommerce.ecommercestore.controller;

import com.ecommerce.ecommercestore.model.User;
import com.ecommerce.ecommercestore.payload.auth.AuthResponse;
import com.ecommerce.ecommercestore.payload.auth.LoginRequest;
import com.ecommerce.ecommercestore.payload.auth.RefreshTokenRequest;
import com.ecommerce.ecommercestore.payload.auth.RegisterRequest;
import com.ecommerce.ecommercestore.security.JwtTokenProvider;
import com.ecommerce.ecommercestore.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtTokenProvider jwtTokenProvider;

    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    public AuthController(AuthenticationManager authenticationManager,
                          UserService userService,
                          JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userService.existsByEmail(registerRequest.getEmail())) {
            return new ResponseEntity<>("Email is already registered!", HttpStatus.BAD_REQUEST);
        }
        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .build();

        userService.createUser(user);

        return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        AuthResponse authResponse = new AuthResponse(token, refreshToken, jwtExpirationDate);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String username = jwtTokenProvider.getUsername(refreshToken);

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null);
        String newAccessToken = jwtTokenProvider.generateToken(authentication);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        AuthResponse authResponse = new AuthResponse(newAccessToken, newRefreshToken, jwtExpirationDate);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register-admin")
    public ResponseEntity<String> registerAdmin(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userService.existsByEmail(registerRequest.getEmail())) {
            return new ResponseEntity<>("Email is already registered!", HttpStatus.BAD_REQUEST);
        }

        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .build();

        userService.createAdminUser(user);

        return new ResponseEntity<>("Admin user registered successfully!", HttpStatus.CREATED);
    }
}