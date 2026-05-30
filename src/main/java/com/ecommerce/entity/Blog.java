package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "blogs")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(unique = true)
    private String slug; // SEO friendly URL

    @Column(columnDefinition = "TEXT")
    private String content;

    private String shortDescription;
    private String thumbnail;
    private String metaTitle;
    private String metaDescription;
    private String tags;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    private Integer views = 0;
    private boolean isPublished = false;
    private boolean isActive = true;

    private LocalDateTime publishedAt;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}