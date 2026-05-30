package com.ecommerce.repository;

import com.ecommerce.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductVariantRepository 
    extends JpaRepository<ProductVariant, Long> {

    List<ProductVariant> findByProductId(Long productId);
    
    List<ProductVariant> findByProductIdAndColor(
        Long productId, String color);
    
    List<ProductVariant> findByProductIdAndSize(
        Long productId, String size);
    
    void deleteByProductId(Long productId);
}
