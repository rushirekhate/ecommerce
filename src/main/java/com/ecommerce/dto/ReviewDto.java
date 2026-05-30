package com.ecommerce.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReviewDto {

    private Long id;
    private String userName;
    private String userImage;
    private Integer rating;
    private String title;
    private String comment;
    private String imageUrl;
    private boolean isVerified;
    private LocalDateTime createdAt;
}