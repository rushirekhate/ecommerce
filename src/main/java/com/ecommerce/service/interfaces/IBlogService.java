package com.ecommerce.service.interfaces;

import com.ecommerce.entity.Blog;
import java.util.List;

public interface IBlogService {

    // Admin CRUD
    Blog addBlog(Blog blog);
    Blog updateBlog(Long id, Blog blog);
    void deleteBlog(Long id);
    Blog getBlogById(Long id);
    List<Blog> getAllBlogs();

    // User
    List<Blog> getPublishedBlogs();
    Blog getBlogBySlug(String slug);
    List<Blog> searchBlogs(String title);

    // Admin
    void publishBlog(Long id);
    void unpublishBlog(Long id);
    void incrementViews(Long id);
}
