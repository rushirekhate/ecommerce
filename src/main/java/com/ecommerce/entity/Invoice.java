package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String invoiceNumber;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Double subtotal;
    private Double discount;
    private Double tax;
    private Double totalAmount;

    private String invoicePdfUrl;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum InvoiceStatus {
        GENERATED, SENT, CANCELLED
    }
}