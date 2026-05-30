package com.ecommerce.repository;

import com.ecommerce.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubCategoryRepository 
    extends JpaRepository<SubCategory, Long> {

    List<SubCategory> findByIsActiveTrue();
    
    List<SubCategory> findByCategoryId(Long categoryId);
    
    List<SubCategory> findByCategoryIdAndIsActiveTrue(
        Long categoryId);
    
    boolean existsByNameAndCategoryId(
        String name, Long categoryId);
}
