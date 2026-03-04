package com.ritesh.biteBytes.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.ritesh.biteBytes.user.dto.AuthRequestDto;
import com.ritesh.biteBytes.user.dto.AuthResponseDto;
import com.ritesh.biteBytes.user.entity.UserEntity;
import com.ritesh.biteBytes.user.repository.UserRepository;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Simple email regex pattern matching the validator library
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    @Override
    public AuthResponseDto registerUser(AuthRequestDto request) {
        try {
            // 1. Check if user already exists
            if (userRepository.existsByEmail(request.getEmail())) {
                return new AuthResponseDto(false, "User already exists");
            }

            // 2. Validate email format
            if (request.getEmail() == null || !EMAIL_PATTERN.matcher(request.getEmail()).matches()) {
                return new AuthResponseDto(false, "Please enter a valid email");
            }

            // 3. Validate strong password
            if (request.getPassword() == null || request.getPassword().length() < 8) {
                return new AuthResponseDto(false, "Please enter a strong password");
            }

            // 4. Create and hash password
            UserEntity newUser = new UserEntity();
            newUser.setName(request.getName());
            newUser.setEmail(request.getEmail());
            newUser.setPassword(passwordEncoder.encode(request.getPassword())); // BCrypt hashing

            if (request.getRole() != null) {
                newUser.setRole(request.getRole());
            }

            // 5. Save user and generate token
            UserEntity savedUser = userRepository.save(newUser);
            String token = createToken(savedUser.getId());

            return new AuthResponseDto(true, token, savedUser.getRole());

        } catch (Exception e) {
            e.printStackTrace();
            return new AuthResponseDto(false, "Error");
        }
    }

    @Override
    public AuthResponseDto loginUser(AuthRequestDto request) {
        try {
            // 1. Find user
            Optional<UserEntity> userOpt = userRepository.findByEmail(request.getEmail());
            if (userOpt.isEmpty()) {
                return new AuthResponseDto(false, "User does not exist");
            }

            UserEntity user = userOpt.get();

            // 2. Compare passwords
            boolean isMatch = passwordEncoder.matches(request.getPassword(), user.getPassword());
            if (!isMatch) {
                return new AuthResponseDto(false, "Invalid credentials");
            }

            // 3. Generate token
            String token = createToken(user.getId());
            return new AuthResponseDto(true, token, user.getRole());

        } catch (Exception e) {
            e.printStackTrace();
            return new AuthResponseDto(false, "Error");
        }
    }

    // Helper method to mirror your Node.js createToken logic
    private String createToken(String id) {
        // We will wire up the actual io.jsonwebtoken library logic here next
        return "jwt_token_placeholder_for_user_" + id;
    }
}
