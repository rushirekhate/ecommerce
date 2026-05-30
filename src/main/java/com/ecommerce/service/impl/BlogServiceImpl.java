package com.ecommerce.service.impl;

import com.ecommerce.entity.Blog;
import com.ecommerce.entity.User;
import com.ecommerce.repository.BlogRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.interfaces.IBlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements IBlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    @Override
    public Blog addBlog(Blog blog) {
        // Slug already exists check
        if (blogRepository.existsBySlug(
                blog.getSlug())) {
            throw new RuntimeException(
                "Slug already exists!");
        }
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog updated) {
        Blog blog = getBlogById(id);
        blog.setTitle(updated.getTitle());
        blog.setContent(updated.getContent());
        blog.setShortDescription(
            updated.getShortDescription());
        blog.setThumbnail(updated.getThumbnail());
        blog.setMetaTitle(updated.getMetaTitle());
        blog.setMetaDescription(
            updated.getMetaDescription());
        blog.setTags(updated.getTags());
        return blogRepository.save(blog);
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Blog getBlogById(Long id) {
        return blogRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(
                "Blog not found!"));
    }

    @Override
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    @Override
    public List<Blog> getPublishedBlogs() {
        return blogRepository
            .findByIsPublishedTrueAndIsActiveTrueOrderByPublishedAtDesc();
    }

    @Override
    public Blog getBlogBySlug(String slug) {
        return blogRepository.findBySlug(slug)
            .orElseThrow(() -> new RuntimeException(
                "Blog not found!"));
    }

    @Override
    public List<Blog> searchBlogs(String title) {
        return blogRepository
            .findByTitleContainingIgnoreCase(title);
    }

    @Override
    public void publishBlog(Long id) {
        Blog blog = getBlogById(id);
        blog.setPublished(true);
        blog.setPublishedAt(LocalDateTime.now());
        blogRepository.save(blog);
    }

    @Override
    public void unpublishBlog(Long id) {
        Blog blog = getBlogById(id);
        blog.setPublished(false);
        blogRepository.save(blog);
    }

    @Override
    public void incrementViews(Long id) {
        Blog blog = getBlogById(id);
        blog.setViews(blog.getViews() + 1);
        blogRepository.save(blog);
    }
}