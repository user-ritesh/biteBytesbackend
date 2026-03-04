package com.ritesh.biteBytes.cart.service;


import com.ritesh.biteBytes.cart.dto.CartRequestDto;
import com.ritesh.biteBytes.cart.dto.CartResponseDto;

public interface CartService {
    CartResponseDto addToCart(CartRequestDto requestDto);
    CartResponseDto removeFromCart(CartRequestDto requestDto);
    CartResponseDto getCart(CartRequestDto requestDto);
}