package cart.controller;

import cart.dto.CartRequestDto;
import cart.dto.CartResponseDto;
import cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartResponseDto> addToCart(
            @RequestAttribute("userId") String userId,
            @RequestBody CartRequestDto request) {
        request.setUserId(userId);
        return ResponseEntity.ok(cartService.addToCart(request));
    }

    @PostMapping("/remove")
    public ResponseEntity<CartResponseDto> removeFromCart(
            @RequestAttribute("userId") String userId,
            @RequestBody CartRequestDto request) {
        request.setUserId(userId);
        return ResponseEntity.ok(cartService.removeFromCart(request));
    }

    @PostMapping("/get")
    public ResponseEntity<CartResponseDto> getCart(@RequestAttribute("userId") String userId,
                                                   @RequestBody CartRequestDto request) {
        request.setUserId(userId);
        return ResponseEntity.ok(cartService.getCart(request));
    }
}
