package cart.mapper;

import cart.dto.CartResponseDto;
import cart.entity.CartEntity;
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
