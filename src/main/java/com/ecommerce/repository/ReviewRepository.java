package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.Review;

import java.util.List;

@Repository
public interface ReviewRepository 
    extends JpaRepository<Review, Long> {

    List<Review> findByProductId(Long productId);
    
    List<Review> findByUserId(Long userId);
    
    List<Review> findByProductIdAndIsApprovedTrue(
        Long productId);
    
    boolean existsByUserIdAndProductId(
        Long userId, Long productId);

    // Average rating
    @Query("SELECT AVG(r.rating) FROM Review r " +
        "WHERE r.product.id = :productId " +
        "AND r.isApproved = true")
    Double getAverageRating(
        @Param("productId") Long productId);

    // Count reviews
    @Query("SELECT COUNT(r) FROM Review r " +
        "WHERE r.product.id = :productId " +
        "AND r.isApproved = true")
    Long countApprovedReviews(
        @Param("productId") Long productId);
}