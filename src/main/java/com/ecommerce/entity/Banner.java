package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "banners")
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String imageUrl;
    private String link;
    private String description;

    @Enumerated(EnumType.STRING)
    private BannerType type;

    private Integer displayOrder = 0;
    private boolean isActive = true;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum BannerType {
        MAIN_SLIDER, OFFER, CATEGORY, POPUP
    }
}