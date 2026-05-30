package com.ecommerce.controller.user;

import com.ecommerce.dto.ApiResponseDto;
import com.ecommerce.dto.ReviewDto;
import com.ecommerce.entity.Review;
import com.ecommerce.service.interfaces.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final IReviewService reviewService;

    // Add review
    @PostMapping("/add")
    public ResponseEntity<ApiResponseDto<ReviewDto>>
            addReview(
                @RequestParam Long userId,
                @RequestParam Long productId,
                @RequestParam(required = false)
                    Long orderId,
                @RequestParam Integer rating,
                @RequestParam String title,
                @RequestParam String comment) {
        Review review = reviewService.addReview(
            userId, productId, orderId,
            rating, title, comment);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Review added!", mapToDto(review)));
    }

    // Get reviews by product
    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponseDto<List<ReviewDto>>>
            getReviewsByProduct(
                @PathVariable Long productId) {
        List<ReviewDto> reviews = reviewService
            .getReviewsByProductId(productId).stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Reviews fetched!", reviews));
    }

    // Get reviews by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDto<List<ReviewDto>>>
            getReviewsByUser(
                @PathVariable Long userId) {
        List<ReviewDto> reviews = reviewService
            .getReviewsByUserId(userId).stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Reviews fetched!", reviews));
    }

    // Get average rating
    @GetMapping("/rating/{productId}")
    public ResponseEntity<ApiResponseDto<Double>>
            getAverageRating(
                @PathVariable Long productId) {
        Double rating = reviewService
            .getAverageRating(productId);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Rating fetched!", rating));
    }

    // Update review
    @PutMapping("/update/{reviewId}")
    public ResponseEntity<ApiResponseDto<ReviewDto>>
            updateReview(
                @PathVariable Long reviewId,
                @RequestParam Integer rating,
                @RequestParam String title,
                @RequestParam String comment) {
        Review review = reviewService.updateReview(
            reviewId, rating, title, comment);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Review updated!", mapToDto(review)));
    }

    // Delete review
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<ApiResponseDto<String>>
            deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Review deleted!", null));
    }

    // Helper
    private ReviewDto mapToDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setUserName(review.getUser().getName());
        dto.setUserImage(
            review.getUser().getProfileImage());
        dto.setRating(review.getRating());
        dto.setTitle(review.getTitle());
        dto.setComment(review.getComment());
        dto.setImageUrl(review.getImageUrl());
        dto.setVerified(review.isVerified());
        dto.setCreatedAt(review.getCreatedAt());
        return dto;
    }
}