package com.ecommerce.repository;

import com.ecommerce.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository 
    extends JpaRepository<Blog, Long> {

    List<Blog> findByIsPublishedTrueAndIsActiveTrueOrderByPublishedAtDesc();
    
    Optional<Blog> findBySlug(String slug);
    
    boolean existsBySlug(String slug);
    
    List<Blog> findByAuthorId(Long authorId);

    List<Blog> findByTitleContainingIgnoreCase(
        String title);
}