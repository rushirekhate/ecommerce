package com.ecommerce.controller.admin;


import com.ecommerce.dto.ApiResponseDto;
import com.ecommerce.entity.Blog;
import com.ecommerce.service.interfaces.IBlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/blogs")
@RequiredArgsConstructor
public class AdminBlogController {

    private final IBlogService blogService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponseDto<Blog>>
            addBlog(@RequestBody Blog blog) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Blog added!",
                blogService.addBlog(blog)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponseDto<Blog>>
            updateBlog(@PathVariable Long id,
                @RequestBody Blog blog) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Blog updated!",
                blogService.updateBlog(id, blog)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDto<String>>
            deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Blog deleted!", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<Blog>>>
            getAllBlogs() {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                blogService.getAllBlogs()));
    }

    @PutMapping("/publish/{id}")
    public ResponseEntity<ApiResponseDto<String>>
            publish(@PathVariable Long id) {
        blogService.publishBlog(id);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Blog published!", null));
    }

    @PutMapping("/unpublish/{id}")
    public ResponseEntity<ApiResponseDto<String>>
            unpublish(@PathVariable Long id) {
        blogService.unpublishBlog(id);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Blog unpublished!", null));
    }
}