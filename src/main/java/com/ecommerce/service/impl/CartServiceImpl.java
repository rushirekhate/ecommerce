package com.ecommerce.service.impl;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.ProductVariant;
import com.ecommerce.entity.User;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.ProductVariantRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.interfaces.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository 
        variantRepository;

    @Override
    public Cart addToCart(Long userId, Long productId,
            Long variantId, Integer quantity) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException(
                "User not found!"));

        Product product = productRepository
            .findById(productId)
            .orElseThrow(() -> new RuntimeException(
                "Product not found!"));

        // Check stock
        if (product.getStock() < quantity) {
            throw new RuntimeException(
                "Insufficient stock!");
        }

        // Check already in cart
        Optional<Cart> existing = variantId != null
            ? cartRepository
                .findByUserIdAndProductIdAndVariantId(
                    userId, productId, variantId)
            : cartRepository
                .findByUserIdAndProductId(
                    userId, productId);

        if (existing.isPresent()) {
            Cart cart = existing.get();
            cart.setQuantity(
                cart.getQuantity() + quantity);
            return cartRepository.save(cart);
        }

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(quantity);

        if (variantId != null) {
            ProductVariant variant = variantRepository
                .findById(variantId)
                .orElseThrow(() -> 
                    new RuntimeException(
                        "Variant not found!"));
            cart.setVariant(variant);
        }

        return cartRepository.save(cart);
    }

    @Override
    public Cart updateQuantity(
            Long cartId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new RuntimeException(
                "Cart item not found!"));
        cart.setQuantity(quantity);
        return cartRepository.save(cart);
    }

    @Override
    public void removeFromCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    @Override
    public void clearCart(Long userId) {
        cartRepository.deleteByUserId(userId);
    }

    @Override
    public List<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public int getCartCount(Long userId) {
        return cartRepository.countByUserId(userId);
    }

    @Override
    public Double getCartTotal(Long userId) {
        List<Cart> cartItems = cartRepository
            .findByUserId(userId);
        return cartItems.stream()
            .mapToDouble(cart -> {
                Double price = cart.getVariant() != null
                    ? cart.getVariant().getPrice()
                    : cart.getProduct().getPrice();
                return price * cart.getQuantity();
            })
            .sum();
    }
}