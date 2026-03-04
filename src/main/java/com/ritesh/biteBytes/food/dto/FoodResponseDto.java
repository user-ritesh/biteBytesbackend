package com.ritesh.biteBytes.food.dto;

public class FoodResponseDto {
    private boolean success;
    private String message;

    public FoodResponseDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}

