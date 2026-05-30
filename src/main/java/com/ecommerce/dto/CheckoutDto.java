package com.ecommerce.dto;

import lombok.Data;

@Data
public class CheckoutDto {

    private Long addressId;
    private String paymentMethod;
    private String couponCode;
}