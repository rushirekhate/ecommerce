package com.ecommerce.service.impl;

import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.entity.Wishlist;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.repository.WishlistRepository;
import com.ecommerce.service.interfaces.ICartService;
import com.ecommerce.service.interfaces.IWishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl 
    implements IWishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ICartService cartService;

    @Override
    public Wishlist addToWishlist(
            Long userId, Long productId) {

        // Already in wishlist check
        if (wishlistRepository
                .existsByUserIdAndProductId(
                    userId, productId)) {
            throw new RuntimeException(
                "Product already in wishlist!");
        }

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException(
                "User not found!"));

        Product product = productRepository
            .findById(productId)
            .orElseThrow(() -> new RuntimeException(
                "Product not found!"));

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setProduct(product);
        return wishlistRepository.save(wishlist);
    }

    @Override
    public void removeFromWishlist(
            Long userId, Long productId) {
        wishlistRepository
            .deleteByUserIdAndProductId(
                userId, productId);
    }

    @Override
    public List<Wishlist> getWishlistByUserId(
            Long userId) {
        return wishlistRepository.findByUserId(userId);
    }

    @Override
    public boolean isProductInWishlist(
            Long userId, Long productId) {
        return wishlistRepository
            .existsByUserIdAndProductId(
                userId, productId);
    }

    @Override
    public void moveToCart(
            Long userId, Long productId) {
        // Add to cart
        cartService.addToCart(
            userId, productId, null, 1);
        // Remove from wishlist
        removeFromWishlist(userId, productId);
    }

    @Override
    public int getWishlistCount(Long userId) {
        return wishlistRepository.countByUserId(userId);
    }
}
