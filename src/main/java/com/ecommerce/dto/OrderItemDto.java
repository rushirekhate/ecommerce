package com.ecommerce.dto;

import lombok.Data;

@Data
public class OrderItemDto {

    private Long id;
    private String productName;
    private String productImage;
    private Integer quantity;
    private Double price;
    private Double totalPrice;
    private String color;
    private String size;
}