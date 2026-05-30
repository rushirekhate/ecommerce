package com.ecommerce.dto;

import com.ecommerce.entity.Coupon.DiscountType;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CouponDto {

    private Long id;
    private String code;
    private String description;
    private DiscountType discountType;
    private Double discountValue;
    private Double minOrderAmount;
    private Double maxDiscountAmount;
    private Integer totalUsageLimit;
    private Integer perUserLimit;
    private boolean isActive;
    private LocalDateTime startDate;
    private LocalDateTime expiryDate;
}