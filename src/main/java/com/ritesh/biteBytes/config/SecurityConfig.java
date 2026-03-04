package com.ritesh.biteBytes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // 1. Tell Spring Security to use the CORS configuration we defined below
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 2. EXPLICITLY ALLOW ALL OPTIONS (PREFLIGHT) REQUESTS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Public routes
                        .requestMatchers("/api/user/login", "/api/user/register", "/images/**", "/").permitAll()

                        // Admin only routes
                        .requestMatchers("/api/admin/**", "/api/food/add", "/api/food/remove").hasRole("ADMIN")

                        // User routes
                        .requestMatchers("/api/cart/**", "/api/order/**").hasAnyRole("USER", "ADMIN")

                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 3. Define the exact CORS policy
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allow both your frontend (5173) and potentially an admin panel (5174)
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://localhost:5174"));

        // Allow standard methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Crucial: Allow the custom "token" header you are using in your frontend
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "token"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}