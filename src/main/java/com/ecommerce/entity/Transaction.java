package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(unique = true)
    private String transactionId;

    private String paymentGateway; // Razorpay, Paytm etc

    private Double amount;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private String gatewayResponse; // JSON response store

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum TransactionStatus {
        INITIATED, SUCCESS, FAILED, REFUNDED, PENDING
    }

    public enum TransactionType {
        PAYMENT, REFUND
    }
}