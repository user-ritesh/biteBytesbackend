package cart.service;


import cart.dto.CartRequestDto;
import cart.dto.CartResponseDto;

public interface CartService {
    CartResponseDto addToCart(CartRequestDto requestDto);
    CartResponseDto removeFromCart(CartRequestDto requestDto);
    CartResponseDto getCart(CartRequestDto requestDto);
}