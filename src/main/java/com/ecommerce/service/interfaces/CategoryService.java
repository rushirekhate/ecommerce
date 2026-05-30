package com.ecommerce.service.interfaces;

import com.ecommerce.entity.Category;
import com.ecommerce.entity.SubCategory;
import java.util.List;

public interface CategoryService {

    // Category CRUD
    Category addCategory(Category category);
    Category updateCategory(Long id, Category category);
    void deleteCategory(Long id);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    List<Category> getActiveCategories();

    // SubCategory CRUD
    SubCategory addSubCategory(SubCategory subCategory);
    SubCategory updateSubCategory(Long id, 
                        SubCategory subCategory);
    void deleteSubCategory(Long id);
    SubCategory getSubCategoryById(Long id);
    List<SubCategory> getAllSubCategories();
    List<SubCategory> getSubCategoriesByCategoryId(
        Long categoryId);
}