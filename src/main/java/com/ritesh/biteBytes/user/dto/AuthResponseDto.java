package com.ritesh.biteBytes.user.dto;

public class AuthResponseDto {
    private boolean success;
    private String message;
    private String token;
    private String role;

    // Constructors
    public AuthResponseDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public AuthResponseDto(boolean success, String token, String role) {
        this.success = success;
        this.token = token;
        this.role = role;
    }

    // Getters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getToken() { return token; }
    public String getRole() { return role; }
}

