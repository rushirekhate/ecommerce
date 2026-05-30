package com.ecommerce.repository;

import com.ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository 
    extends JpaRepository<Cart, Long> {

    List<Cart> findByUserId(Long userId);
    
    Optional<Cart> findByUserIdAndProductId(
        Long userId, Long productId);
    
    Optional<Cart> findByUserIdAndProductIdAndVariantId(
        Long userId, Long productId, Long variantId);
    
    void deleteByUserId(Long userId);
    
    int countByUserId(Long userId);
}
