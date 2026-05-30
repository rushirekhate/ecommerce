package com.ecommerce.dto;

import lombok.Data;

@Data
public class ProductVariantDto {

    private Long id;
    private String color;
    private String size;
    private Double price;
    private Double discountPrice;
    private Integer stock;
}