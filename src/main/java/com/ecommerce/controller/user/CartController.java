package com.ecommerce.controller.user;

import com.ecommerce.dto.ApiResponseDto;
import com.ecommerce.dto.CartDto;
import com.ecommerce.entity.Cart;
import com.ecommerce.service.interfaces.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final ICartService cartService;

    // Add to cart
    @PostMapping("/add")
    public ResponseEntity<ApiResponseDto<CartDto>>
            addToCart(
                @RequestParam Long userId,
                @RequestParam Long productId,
                @RequestParam(required = false)
                    Long variantId,
                @RequestParam Integer quantity) {
        Cart cart = cartService.addToCart(
            userId, productId, variantId, quantity);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Added to cart!", mapToDto(cart)));
    }

    // Get cart
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponseDto<List<CartDto>>>
            getCart(@PathVariable Long userId) {
        List<CartDto> cartItems = cartService
            .getCartByUserId(userId).stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Cart fetched!", cartItems));
    }

    // Update quantity
    @PutMapping("/update/{cartId}")
    public ResponseEntity<ApiResponseDto<CartDto>>
            updateQuantity(
                @PathVariable Long cartId,
                @RequestParam Integer quantity) {
        Cart cart = cartService.updateQuantity(
            cartId, quantity);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Cart updated!", mapToDto(cart)));
    }

    // Remove from cart
    @DeleteMapping("/remove/{cartId}")
    public ResponseEntity<ApiResponseDto<String>>
            removeFromCart(
                @PathVariable Long cartId) {
        cartService.removeFromCart(cartId);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Removed from cart!", null));
    }

    // Clear cart
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<ApiResponseDto<String>>
            clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Cart cleared!", null));
    }

    // Cart count
    @GetMapping("/count/{userId}")
    public ResponseEntity<ApiResponseDto<Integer>>
            getCartCount(@PathVariable Long userId) {
        int count = cartService.getCartCount(userId);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Cart count!", count));
    }

    // Cart total
    @GetMapping("/total/{userId}")
    public ResponseEntity<ApiResponseDto<Double>>
            getCartTotal(@PathVariable Long userId) {
        Double total = cartService.getCartTotal(userId);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Cart total!", total));
    }

    // Helper
    private CartDto mapToDto(Cart cart) {
        CartDto dto = new CartDto();
        dto.setId(cart.getId());
        dto.setProductId(cart.getProduct().getId());
        dto.setProductName(
            cart.getProduct().getName());
        dto.setQuantity(cart.getQuantity());

        Double price = cart.getVariant() != null
            ? cart.getVariant().getPrice()
            : cart.getProduct().getPrice();
        dto.setPrice(price);
        dto.setTotalPrice(price * cart.getQuantity());

        if (cart.getVariant() != null) {
            dto.setVariantId(
                cart.getVariant().getId());
            dto.setColor(cart.getVariant().getColor());
            dto.setSize(cart.getVariant().getSize());
        }

        if (cart.getProduct().getImages() != null
                && !cart.getProduct()
                    .getImages().isEmpty()) {
            dto.setProductImage(
                cart.getProduct()
                    .getImages().get(0).getImageUrl());
        }
        return dto;
    }
}