package com.ecommerce.dto;

import lombok.Data;

@Data
public class WishlistDto {

    private Long id;
    private Long productId;
    private String productName;
    private String productImage;
    private Double price;
    private Double discountPrice;
    private Double rating;
    private boolean isInStock;
}