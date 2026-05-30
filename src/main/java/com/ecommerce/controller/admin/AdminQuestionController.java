package com.ecommerce.controller.admin;


import com.ecommerce.dto.ApiResponseDto;
import com.ecommerce.entity.ProductQuestion;
import com.ecommerce.service.interfaces
    .IProductQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/questions")
@RequiredArgsConstructor
public class AdminQuestionController {

    private final IProductQuestionService 
        questionService;

    @GetMapping("/pending")
    public ResponseEntity<ApiResponseDto<List<ProductQuestion>>>
            getPending() {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                questionService.getPendingQuestions()));
    }

    @PutMapping("/answer/{questionId}")
    public ResponseEntity<ApiResponseDto<ProductQuestion>>
            answer(@PathVariable Long questionId,
                @RequestParam Long adminId,
                @RequestParam String answer) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Answered!",
                questionService.answerQuestion(
                    questionId, adminId, answer)));
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<ApiResponseDto<String>>
            approve(@PathVariable Long id) {
        questionService.approveQuestion(id);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Question approved!", null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDto<String>>
            delete(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Question deleted!", null));
    }
}