package com.ecommerce.repository;

import com.ecommerce.entity.ProductQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductQuestionRepository 
    extends JpaRepository<ProductQuestion, Long> {

    List<ProductQuestion> findByProductId(Long productId);
    
    List<ProductQuestion> findByProductIdAndIsApprovedTrue(
        Long productId);
    
    List<ProductQuestion> findByUserId(Long userId);
    
    List<ProductQuestion> findByIsApprovedFalse();
    
    int countByProductIdAndIsAnsweredTrue(Long productId);
}