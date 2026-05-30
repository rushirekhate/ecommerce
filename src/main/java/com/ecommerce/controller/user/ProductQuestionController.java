package com.ecommerce.controller.user;

import com.ecommerce.dto.ApiResponseDto;
import com.ecommerce.entity.ProductQuestion;
import com.ecommerce.service.interfaces
    .IProductQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class ProductQuestionController {

    private final IProductQuestionService 
        questionService;

    @PostMapping("/ask")
    public ResponseEntity<ApiResponseDto<ProductQuestion>>
            askQuestion(
                @RequestParam Long userId,
                @RequestParam Long productId,
                @RequestParam String question) {
        ProductQuestion pq = questionService
            .askQuestion(userId, productId, question);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Question submitted!", pq));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponseDto<List<ProductQuestion>>>
            getByProduct(
                @PathVariable Long productId) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                questionService
                    .getApprovedQuestions(productId)));
    }
}