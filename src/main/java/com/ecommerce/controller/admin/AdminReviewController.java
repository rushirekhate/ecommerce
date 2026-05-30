package com.ecommerce.controller.admin;


import com.ecommerce.dto.ApiResponseDto;
import com.ecommerce.entity.Review;
import com.ecommerce.service.interfaces.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/reviews")
@RequiredArgsConstructor
public class AdminReviewController {

    private final IReviewService reviewService;

    @GetMapping("/pending")
    public ResponseEntity<ApiResponseDto<List<Review>>>
            getPendingReviews() {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                reviewService.getPendingReviews()));
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<ApiResponseDto<String>>
            approve(@PathVariable Long id) {
        reviewService.approveReview(id);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Review approved!", null));
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<ApiResponseDto<String>>
            reject(@PathVariable Long id) {
        reviewService.rejectReview(id);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Review rejected!", null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDto<String>>
            delete(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Review deleted!", null));
    }
}