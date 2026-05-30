package com.ecommerce.service.interfaces;

import com.ecommerce.entity.Cart;
import java.util.List;

public interface ICartService {

    // Cart Operations
    Cart addToCart(Long userId, Long productId,
                  Long variantId, Integer quantity);
    
    Cart updateQuantity(Long cartId, Integer quantity);
    
    void removeFromCart(Long cartId);
    
    void clearCart(Long userId);
    
    List<Cart> getCartByUserId(Long userId);
    
    int getCartCount(Long userId);
    
    Double getCartTotal(Long userId);
}
