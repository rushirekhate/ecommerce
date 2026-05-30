package com.ecommerce.dto;

import lombok.Data;

@Data
public class CartDto {

    private Long id;
    private Long productId;
    private String productName;
    private String productImage;
    private Double price;
    private Integer quantity;
    private Double totalPrice;
    private Long variantId;
    private String color;
    private String size;
}