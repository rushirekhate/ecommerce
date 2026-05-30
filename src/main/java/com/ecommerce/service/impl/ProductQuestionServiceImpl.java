package com.ecommerce.service.impl;

import com.ecommerce.entity.Product;
import com.ecommerce.entity.ProductQuestion;
import com.ecommerce.entity.User;
import com.ecommerce.repository.ProductQuestionRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.interfaces.IProductQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductQuestionServiceImpl 
    implements IProductQuestionService {

    private final ProductQuestionRepository 
        productQuestionRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public ProductQuestion askQuestion(
            Long userId, Long productId,
            String question) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException(
                "User not found!"));

        Product product = productRepository
            .findById(productId)
            .orElseThrow(() -> new RuntimeException(
                "Product not found!"));

        ProductQuestion pq = new ProductQuestion();
        pq.setUser(user);
        pq.setProduct(product);
        pq.setQuestion(question);
        pq.setApproved(false);
        pq.setAnswered(false);

        return productQuestionRepository.save(pq);
    }

    @Override
    public List<ProductQuestion> 
        getQuestionsByProductId(Long productId) {
        return productQuestionRepository
            .findByProductId(productId);
    }

    @Override
    public List<ProductQuestion> getApprovedQuestions(
            Long productId) {
        return productQuestionRepository
            .findByProductIdAndIsApprovedTrue(productId);
    }

    @Override
    public ProductQuestion answerQuestion(
            Long questionId, Long adminId,
            String answer) {

        ProductQuestion pq = productQuestionRepository
            .findById(questionId)
            .orElseThrow(() -> new RuntimeException(
                "Question not found!"));

        User admin = userRepository.findById(adminId)
            .orElseThrow(() -> new RuntimeException(
                "Admin not found!"));

        pq.setAnswer(answer);
        pq.setAnsweredBy(admin);
        pq.setAnswered(true);
        pq.setAnsweredAt(LocalDateTime.now());

        return productQuestionRepository.save(pq);
    }

    @Override
    public void approveQuestion(Long questionId) {
        ProductQuestion pq = productQuestionRepository
            .findById(questionId)
            .orElseThrow(() -> new RuntimeException(
                "Question not found!"));
        pq.setApproved(true);
        productQuestionRepository.save(pq);
    }

    @Override
    public void deleteQuestion(Long questionId) {
        productQuestionRepository
            .deleteById(questionId);
    }

    @Override
    public List<ProductQuestion> getPendingQuestions() {
        return productQuestionRepository
            .findByIsApprovedFalse();
    }

    @Override
    public int getAnsweredQuestionsCount(
            Long productId) {
        return productQuestionRepository
            .countByProductIdAndIsAnsweredTrue(productId);
    }
}