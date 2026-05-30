package com.ecommerce.controller.user;

import com.ecommerce.dto.ApiResponseDto;
import com.ecommerce.dto.WishlistDto;
import com.ecommerce.entity.Wishlist;
import com.ecommerce.service.interfaces.IWishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final IWishlistService wishlistService;

    // Add to wishlist
    @PostMapping("/add")
    public ResponseEntity<ApiResponseDto<WishlistDto>>
            addToWishlist(
                @RequestParam Long userId,
                @RequestParam Long productId) {
        Wishlist wishlist = wishlistService
            .addToWishlist(userId, productId);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Added to wishlist!",
                mapToDto(wishlist)));
    }

    // Get wishlist
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponseDto<List<WishlistDto>>>
            getWishlist(@PathVariable Long userId) {
        List<WishlistDto> wishlistItems = wishlistService
            .getWishlistByUserId(userId).stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Wishlist fetched!", wishlistItems));
    }

    // Remove from wishlist
    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponseDto<String>>
            removeFromWishlist(
                @RequestParam Long userId,
                @RequestParam Long productId) {
        wishlistService.removeFromWishlist(
            userId, productId);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Removed from wishlist!", null));
    }

    // Check if in wishlist
    @GetMapping("/check")
    public ResponseEntity<ApiResponseDto<Boolean>>
            checkWishlist(
                @RequestParam Long userId,
                @RequestParam Long productId) {
        boolean exists = wishlistService
            .isProductInWishlist(userId, productId);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Checked!", exists));
    }

    // Move to cart
    @PostMapping("/move-to-cart")
    public ResponseEntity<ApiResponseDto<String>>
            moveToCart(
                @RequestParam Long userId,
                @RequestParam Long productId) {
        wishlistService.moveToCart(userId, productId);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Moved to cart!", null));
    }

    // Wishlist count
    @GetMapping("/count/{userId}")
    public ResponseEntity<ApiResponseDto<Integer>>
            getWishlistCount(
                @PathVariable Long userId) {
        int count = wishlistService
            .getWishlistCount(userId);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Wishlist count!", count));
    }

    // Helper
    private WishlistDto mapToDto(Wishlist wishlist) {
        WishlistDto dto = new WishlistDto();
        dto.setId(wishlist.getId());
        dto.setProductId(
            wishlist.getProduct().getId());
        dto.setProductName(
            wishlist.getProduct().getName());
        dto.setPrice(wishlist.getProduct().getPrice());
        dto.setDiscountPrice(
            wishlist.getProduct().getDiscountPrice());
        dto.setRating(
            wishlist.getProduct().getRating());
        dto.setInStock(
            wishlist.getProduct().getStock() > 0);

        if (wishlist.getProduct().getImages() != null
                && !wishlist.getProduct()
                    .getImages().isEmpty()) {
            dto.setProductImage(
                wishlist.getProduct()
                    .getImages().get(0).getImageUrl());
        }
        return dto;
    }
}
