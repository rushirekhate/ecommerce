package com.ecommerce.service.interfaces;

import com.ecommerce.entity.Wishlist;
import java.util.List;

public interface IWishlistService {

    Wishlist addToWishlist(Long userId, Long productId);
    
    void removeFromWishlist(Long userId, Long productId);
    
    List<Wishlist> getWishlistByUserId(Long userId);
    
    boolean isProductInWishlist(Long userId, 
                               Long productId);
    
    void moveToCart(Long userId, Long productId);
    
    int getWishlistCount(Long userId);
}