package com.ecommerce.service.interfaces;

import com.ecommerce.entity.Transaction;
import com.ecommerce.entity.Transaction.TransactionStatus;
import java.time.LocalDateTime;
import java.util.List;

public interface ITransactionService {

    Transaction createTransaction(Long orderId,
                                 Long userId,
                                 Double amount,
                                 String paymentGateway);

    Transaction updateTransactionStatus(
                                 Long transactionId,
                                 TransactionStatus status);

    Transaction getTransactionById(Long id);
    
    List<Transaction> getTransactionsByUserId(
        Long userId);
    
    List<Transaction> getTransactionsByOrderId(
        Long orderId);

    // Admin
    List<Transaction> getAllTransactions();
    
    Double getRevenueByDateRange(LocalDateTime start,
                                LocalDateTime end);
}