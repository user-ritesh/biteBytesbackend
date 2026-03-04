package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs
                .cors(cors -> cors.configure(http))
                // Enforce strictly stateless session management
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Public routes
                        .requestMatchers("/api/user/login", "/api/user/register", "/images/**", "/").permitAll()

                        // Admin only routes (Replaces adminAuth.js)
                        // Any route under /api/admin requires the ADMIN role
                        .requestMatchers("/api/admin/**", "/api/food/add", "/api/food/remove").hasRole("ADMIN")

                        // User routes (Replaces authMiddleware.js)
                        // All other API routes require at least a standard USER role
                        .requestMatchers("/api/cart/**", "/api/order/**").hasAnyRole("USER", "ADMIN")

                        .anyRequest().authenticated()
                );

        // Add our custom JWT filter before the standard authentication filter
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
