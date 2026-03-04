package com.ritesh.biteBytes.cart.mapper;

import com.ritesh.biteBytes.cart.dto.CartResponseDto;
import com.ritesh.biteBytes.cart.entity.CartEntity;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {
    public CartResponseDto toDto(boolean success, String message, CartEntity entity) {
        return new CartResponseDto(
                success,
                message,
                entity != null ? entity.getCartData() : null
        );
    }
}
