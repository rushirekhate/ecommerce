package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer rating; // 1 to 5

    private String title;
    private String comment;
    private String imageUrl;

    private boolean isVerified = false;
    private boolean isApproved = false;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}