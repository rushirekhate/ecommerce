package com.ecommerce.service.impl;

import com.ecommerce.entity.Category;
import com.ecommerce.entity.SubCategory;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.SubCategoryRepository;
import com.ecommerce.service.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl 
    implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository 
        subCategoryRepository;

    @Override
    public Category addCategory(Category category) {
        if (categoryRepository.existsByName(
                category.getName())) {
            throw new RuntimeException(
                "Category already exists!");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(
            Long id, Category updatedCategory) {
        Category category = getCategoryById(id);
        category.setName(updatedCategory.getName());
        category.setDescription(
            updatedCategory.getDescription());
        category.setImage(updatedCategory.getImage());
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(
                "Category not found!"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> getActiveCategories() {
        return categoryRepository.findByIsActiveTrue();
    }

    @Override
    public SubCategory addSubCategory(
            SubCategory subCategory) {
        if (subCategoryRepository
                .existsByNameAndCategoryId(
                    subCategory.getName(),
                    subCategory.getCategory().getId())) {
            throw new RuntimeException(
                "SubCategory already exists!");
        }
        return subCategoryRepository.save(subCategory);
    }

    @Override
    public SubCategory updateSubCategory(
            Long id, SubCategory updated) {
        SubCategory sub = getSubCategoryById(id);
        sub.setName(updated.getName());
        sub.setDescription(updated.getDescription());
        sub.setImage(updated.getImage());
        return subCategoryRepository.save(sub);
    }

    @Override
    public void deleteSubCategory(Long id) {
        subCategoryRepository.deleteById(id);
    }

    @Override
    public SubCategory getSubCategoryById(Long id) {
        return subCategoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(
                "SubCategory not found!"));
    }

    @Override
    public List<SubCategory> getAllSubCategories() {
        return subCategoryRepository.findAll();
    }

    @Override
    public List<SubCategory> 
        getSubCategoriesByCategoryId(Long categoryId) {
        return subCategoryRepository
            .findByCategoryId(categoryId);
    }
}