package com.ecommerce.controller.user;


import com.ecommerce.dto.ApiResponseDto;
import com.ecommerce.entity.Blog;
import com.ecommerce.service.interfaces.IBlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final IBlogService blogService;

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<Blog>>>
            getPublishedBlogs() {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                blogService.getPublishedBlogs()));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponseDto<Blog>>
            getBySlug(@PathVariable String slug) {
        Blog blog = blogService.getBlogBySlug(slug);
        blogService.incrementViews(blog.getId());
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Fetched!", blog));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponseDto<List<Blog>>>
            search(@RequestParam String title) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                blogService.searchBlogs(title)));
    }
}