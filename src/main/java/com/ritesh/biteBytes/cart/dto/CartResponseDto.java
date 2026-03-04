package com.ritesh.biteBytes.cart.dto;
import java.util.Map;


public class CartResponseDto {
    private boolean success;
    private String message;
    private Map<String, Integer> cartData;

    public CartResponseDto(boolean success, String message, Map<String, Integer> cartData) {
        this.success = success;
        this.message = message;
        this.cartData = cartData;
    }

    // Getters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Map<String, Integer> getCartData() { return cartData; }
}
