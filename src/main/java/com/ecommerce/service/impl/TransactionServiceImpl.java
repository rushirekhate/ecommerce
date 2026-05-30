package com.ecommerce.service.impl;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.Transaction;
import com.ecommerce.entity.Transaction.TransactionStatus;
import com.ecommerce.entity.User;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.TransactionRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.interfaces.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl 
    implements ITransactionService {

    private final TransactionRepository 
        transactionRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public Transaction createTransaction(
            Long orderId, Long userId,
            Double amount, String paymentGateway) {

        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException(
                "Order not found!"));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException(
                "User not found!"));

        Transaction transaction = new Transaction();
        transaction.setOrder(order);
        transaction.setUser(user);
        transaction.setAmount(amount);
        transaction.setPaymentGateway(paymentGateway);
        transaction.setTransactionId(
            generateTransactionId());
        transaction.setStatus(
            TransactionStatus.INITIATED);
        transaction.setType(
            Transaction.TransactionType.PAYMENT);

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction updateTransactionStatus(
            Long transactionId,
            TransactionStatus status) {
        Transaction transaction = 
            transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException(
                    "Transaction not found!"));
        transaction.setStatus(status);
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(
                "Transaction not found!"));
    }

    @Override
    public List<Transaction> getTransactionsByUserId(
            Long userId) {
        return transactionRepository
            .findByUserId(userId);
    }

    @Override
    public List<Transaction> getTransactionsByOrderId(
            Long orderId) {
        return transactionRepository
            .findByOrderId(orderId);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Double getRevenueByDateRange(
            LocalDateTime start, LocalDateTime end) {
        return transactionRepository
            .getRevenueByDateRange(start, end);
    }

    private String generateTransactionId() {
        return "TXN-" + System.currentTimeMillis() +
            "-" + UUID.randomUUID()
                .toString().substring(0, 6)
                .toUpperCase();
    }
}