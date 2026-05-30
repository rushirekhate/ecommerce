package com.ecommerce.service.impl;

import com.ecommerce.entity.Product;
import com.ecommerce.entity.Review;
import com.ecommerce.entity.User;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.ReviewRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.interfaces.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl 
    implements IReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    public Review addReview(Long userId, Long productId,
            Long orderId, Integer rating,
            String title, String comment) {

        // Already reviewed check
        if (reviewRepository.existsByUserIdAndProductId(
                userId, productId)) {
            throw new RuntimeException(
                "Already reviewed this product!");
        }

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException(
                "User not found!"));

        Product product = productRepository
            .findById(productId)
            .orElseThrow(() -> new RuntimeException(
                "Product not found!"));

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(rating);
        review.setTitle(title);
        review.setComment(comment);
        review.setVerified(true);

        if (orderId != null) {
            orderRepository.findById(orderId)
                .ifPresent(review::setOrder);
        }

        Review saved = reviewRepository.save(review);

        // Update product rating
        updateProductRating(productId);

        return saved;
    }

    @Override
    public Review updateReview(Long reviewId,
            Integer rating, String title, 
            String comment) {
        Review review = reviewRepository
            .findById(reviewId)
            .orElseThrow(() -> new RuntimeException(
                "Review not found!"));
        review.setRating(rating);
        review.setTitle(title);
        review.setComment(comment);
        Review saved = reviewRepository.save(review);
        updateProductRating(
            review.getProduct().getId());
        return saved;
    }

    @Override
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository
            .findById(reviewId)
            .orElseThrow(() -> new RuntimeException(
                "Review not found!"));
        Long productId = review.getProduct().getId();
        reviewRepository.deleteById(reviewId);
        updateProductRating(productId);
    }

    @Override
    public List<Review> getReviewsByProductId(
            Long productId) {
        return reviewRepository
            .findByProductIdAndIsApprovedTrue(productId);
    }

    @Override
    public List<Review> getReviewsByUserId(
            Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    @Override
    public Double getAverageRating(Long productId) {
        return reviewRepository
            .getAverageRating(productId);
    }

    @Override
    public Long getReviewCount(Long productId) {
        return reviewRepository
            .countApprovedReviews(productId);
    }

    @Override
    public void approveReview(Long reviewId) {
        Review review = reviewRepository
            .findById(reviewId)
            .orElseThrow(() -> new RuntimeException(
                "Review not found!"));
        review.setApproved(true);
        reviewRepository.save(review);
        updateProductRating(
            review.getProduct().getId());
    }

    @Override
    public void rejectReview(Long reviewId) {
        Review review = reviewRepository
            .findById(reviewId)
            .orElseThrow(() -> new RuntimeException(
                "Review not found!"));
        review.setApproved(false);
        reviewRepository.save(review);
    }

    @Override
    public List<Review> getPendingReviews() {
        return reviewRepository.findAll().stream()
            .filter(r -> !r.isApproved())
            .toList();
    }

    // Helper method
    private void updateProductRating(Long productId) {
        Double avgRating = reviewRepository
            .getAverageRating(productId);
        Long totalReviews = reviewRepository
            .countApprovedReviews(productId);

        Product product = productRepository
            .findById(productId)
            .orElseThrow(() -> new RuntimeException(
                "Product not found!"));

        product.setRating(
            avgRating != null ? avgRating : 0.0);
        product.setTotalReviews(
            totalReviews != null ? 
            totalReviews.intValue() : 0);
        productRepository.save(product);
    }
}