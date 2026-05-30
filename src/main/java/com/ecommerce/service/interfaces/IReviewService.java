package com.ecommerce.service.interfaces;

import com.ecommerce.entity.Review;
import java.util.List;

public interface IReviewService {

    Review addReview(Long userId, Long productId,
                    Long orderId, Integer rating,
                    String title, String comment);
    
    Review updateReview(Long reviewId, Integer rating,
                       String title, String comment);
    
    void deleteReview(Long reviewId);
    
    List<Review> getReviewsByProductId(Long productId);
    
    List<Review> getReviewsByUserId(Long userId);
    
    Double getAverageRating(Long productId);
    
    Long getReviewCount(Long productId);

    // Admin
    void approveReview(Long reviewId);
    void rejectReview(Long reviewId);
    List<Review> getPendingReviews();
}