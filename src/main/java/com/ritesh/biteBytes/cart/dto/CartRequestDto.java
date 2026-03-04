package com.ritesh.biteBytes.cart.dto;

public class CartRequestDto {
    private String userId;
    private String itemId;

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getItemId() { return itemId; }
    public void setItemId(String itemId) { this.itemId = itemId; }
}
