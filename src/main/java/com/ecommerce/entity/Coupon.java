package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    private String description;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    private Double discountValue;
    private Double minOrderAmount;
    private Double maxDiscountAmount;

    private Integer totalUsageLimit;
    private Integer usedCount = 0;
    private Integer perUserLimit = 1;

    private boolean isActive = true;

    private LocalDateTime startDate;
    private LocalDateTime expiryDate;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum DiscountType {
        PERCENTAGE, FLAT
    }
}
