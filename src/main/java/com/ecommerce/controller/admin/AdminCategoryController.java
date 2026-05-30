package com.ecommerce.controller.admin;

import com.ecommerce.dto.ApiResponseDto;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.SubCategory;
import com.ecommerce.service.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    // Category CRUD
    @PostMapping("/add")
    public ResponseEntity<ApiResponseDto<Category>>
            addCategory(
                @RequestBody Category category) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Category added!",
                categoryService
                    .addCategory(category)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponseDto<Category>>
            updateCategory(@PathVariable Long id,
                @RequestBody Category category) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Category updated!",
                categoryService
                    .updateCategory(id, category)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDto<String>>
            deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Category deleted!", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<Category>>>
            getAllCategories() {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                categoryService.getAllCategories()));
    }

    // SubCategory CRUD
    @PostMapping("/sub/add")
    public ResponseEntity<ApiResponseDto<SubCategory>>
            addSubCategory(
                @RequestBody SubCategory subCategory) {
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "SubCategory added!",
                categoryService
                    .addSubCategory(subCategory)));
    }

    @PutMapping("/sub/update/{id}")
    public ResponseEntity<ApiResponseDto<SubCategory>>
            updateSubCategory(@PathVariable Long id,
                @RequestBody SubCategory subCategory) {
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "SubCategory updated!",
                categoryService.updateSubCategory(
                    id, subCategory)));
    }

    @DeleteMapping("/sub/delete/{id}")
    public ResponseEntity<ApiResponseDto<String>>
            deleteSubCategory(@PathVariable Long id) {
        categoryService.deleteSubCategory(id);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "SubCategory deleted!", null));
    }

    @GetMapping("/sub/{categoryId}")
    public ResponseEntity<ApiResponseDto<List<SubCategory>>>
            getSubCategories(
                @PathVariable Long categoryId) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                categoryService
                    .getSubCategoriesByCategoryId(
                        categoryId)));
    }
}