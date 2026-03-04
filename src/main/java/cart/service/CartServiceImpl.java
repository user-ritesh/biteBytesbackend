package cart.service;

import cart.dto.CartRequestDto;
import cart.dto.CartResponseDto;
import cart.entity.CartEntity;
import cart.mapper.CartMapper;
import cart.repository.CartReadRepo;
import cart.repository.CartWriteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartReadRepo cartReadRepo;

    @Autowired
    private CartWriteRepo cartWriteRepo;

    @Autowired
    private CartMapper cartMapper;

    @Override
    public CartResponseDto addToCart(CartRequestDto request) {
        try {
            // Atomically increment the item by 1
            cartWriteRepo.updateCartItemQuantity(request.getUserId(), request.getItemId(), 1);
            return new CartResponseDto(true, "Added To Cart", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new CartResponseDto(false, "Error", null);
        }
    }

    @Override
    public CartResponseDto removeFromCart(CartRequestDto request) {
        try {
            Optional<CartEntity> userOpt = cartReadRepo.findById(request.getUserId());

            if (userOpt.isPresent()) {
                CartEntity user = userOpt.get();
                Integer currentQty = user.getCartData().getOrDefault(request.getItemId(), 0);

                if (currentQty > 0) {
                    // Atomically decrement the item by 1
                    cartWriteRepo.updateCartItemQuantity(request.getUserId(), request.getItemId(), -1);
                }
            }
            return new CartResponseDto(true, "Removed From Cart", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new CartResponseDto(false, "Error", null);
        }
    }

    @Override
    public CartResponseDto getCart(CartRequestDto request) {
        try {
            Optional<CartEntity> userOpt = cartReadRepo.findById(request.getUserId());
            return cartMapper.toDto(true, "Success", userOpt.orElse(null));
        } catch (Exception e) {
            e.printStackTrace();
            return new CartResponseDto(false, "Error", null);
        }
    }
}
