package com.ecommerce.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {

    private Long id;
    private String orderNumber;
    private Double totalAmount;
    private Double discountAmount;
    private Double finalAmount;
    private String status;
    private String paymentMethod;
    private String paymentStatus;
    private String addressLine;
    private String city;
    private String state;
    private String pincode;
    private LocalDateTime createdAt;
    private List<OrderItemDto> orderItems;
}
