package com.ecommerce.ecommercestore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // Updated annotation
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Updated annotation for method-level security
public class SecurityConfig {

    // Removed: private CustomUserDetailsService userDetailsService;
    // Removed: private JwtAuthenticationEntryPoint authenticationEntryPoint;
    // Removed: private JwtAuthenticationFilter authenticationFilter;

    // The constructor is updated to remove the parameters that are no longer fields.
    // If CustomUserDetailsService, JwtAuthenticationEntryPoint, and JwtAuthenticationFilter
    // are not used/injected anywhere, you can remove this constructor entirely.
    // For now, keeping it minimal as per the request to only remove the fields.
    public SecurityConfig() {
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Modern way to disable CSRF
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless sessions for REST API
                )
                // Authentication entry point and filter are removed as per request for a simplified setup
                .authorizeHttpRequests(authorize -> // Modern way to configure authorization
                        authorize
                                // Allow all requests to auth endpoints (register, login, refresh)
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                // Allow public GET access to product and category endpoints
                                .requestMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                                // Since you mentioned "no security" for products/categories,
                                // and potentially no other security components are set up,
                                // we'll permit all other requests for now for simplicity.
                                // In a real app, you'd secure these.
                                .anyRequest().permitAll() // Temporarily permit all other requests
                );

        // JwtAuthenticationFilter is no longer added if not injected/defined.
        // If you were to re-introduce JWT, you'd add this back with a proper filter bean.
        // http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
