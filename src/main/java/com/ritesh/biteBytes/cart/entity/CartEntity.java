package com.ritesh.biteBytes.cart.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "users")
public class CartEntity {

    @Id
    private String id; // This maps to the userId

    private Map<String, Integer> cartData = new HashMap<>();

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Map<String, Integer> getCartData() { return cartData; }
    public void setCartData(Map<String, Integer> cartData) { this.cartData = cartData; }
}