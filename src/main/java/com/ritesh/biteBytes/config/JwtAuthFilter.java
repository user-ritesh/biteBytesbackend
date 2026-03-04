package com.ritesh.biteBytes.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.ritesh.biteBytes.user.entity.UserEntity;
import com.ritesh.biteBytes.user.repository.UserRepository;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Look for the exact header you used in Node.js
        String token = request.getHeader("token");

        if (token != null && jwtUtil.isTokenValid(token)) {
            try {
                // 2. Decode token and get ID
                String userId = jwtUtil.extractUserId(token);

                // 3. Fetch user to get their role (for Admin auth)
                Optional<UserEntity> userOpt = userRepository.findById(userId);

                if (userOpt.isPresent()) {
                    UserEntity user = userOpt.get();

                    // 4. Set the role into Spring Security context (handles user vs admin)
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase());

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            user.getId(), null, Collections.singletonList(authority));

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    // 5. Pass the userId to the controller, replacing req.body.userId = token_decode.id;
                    request.setAttribute("userId", user.getId());
                }
            } catch (Exception e) {
                System.out.println("Token validation error: " + e.getMessage());
            }
        }

        // 6. Continue the filter chain (equivalent to next())
        filterChain.doFilter(request, response);
    }
}
