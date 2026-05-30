package com.ecommerce.repository;

import com.ecommerce.entity.Transaction;
import com.ecommerce.entity.Transaction.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository 
    extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionId(
        String transactionId);
    
    List<Transaction> findByUserId(Long userId);
    
    List<Transaction> findByOrderId(Long orderId);
    
    List<Transaction> findByStatus(
        TransactionStatus status);

    // Revenue by date range
    @Query("SELECT SUM(t.amount) FROM Transaction t " +
        "WHERE t.status = 'SUCCESS' AND " +
        "t.createdAt BETWEEN :start AND :end")
    Double getRevenueByDateRange(
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end);
}