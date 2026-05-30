package com.ecommerce.service.interfaces;

import com.ecommerce.entity.ProductQuestion;
import java.util.List;

public interface IProductQuestionService {

    // User
    ProductQuestion askQuestion(Long userId,
                               Long productId,
                               String question);

    List<ProductQuestion> getQuestionsByProductId(
        Long productId);

    List<ProductQuestion> getApprovedQuestions(
        Long productId);

    // Admin
    ProductQuestion answerQuestion(Long questionId,
                                  Long adminId,
                                  String answer);

    void approveQuestion(Long questionId);
    void deleteQuestion(Long questionId);
    
    List<ProductQuestion> getPendingQuestions();
    
    int getAnsweredQuestionsCount(Long productId);
}