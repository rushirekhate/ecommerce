package com.ecommerce.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class ProductDto {

    private Long id;

    @NotBlank(message = "Product name is required")
    private String name;

    private String description;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price cannot be negative")
    private Double price;

    private Double discountPrice;
    private String brand;

    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;

    private Double rating;
    private Integer totalReviews;
    private boolean isActive;
    private boolean isFeatured;
    private String categoryName;
    private String subCategoryName;
    private List<String> images;
    private List<ProductVariantDto> variants;
}