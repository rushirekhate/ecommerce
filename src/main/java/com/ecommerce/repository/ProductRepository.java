package com.ecommerce.repository;

import com.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Repository
public interface ProductRepository 
    extends JpaRepository<Product, Long> {

    List<Product> findByIsActiveTrue();
    
    List<Product> findByCategoryId(Long categoryId);
    
    List<Product> findBySubCategoryId(Long subCategoryId);
    
    List<Product> findByIsFeaturedTrue();
    
    List<Product> findByCategoryIdAndIsActiveTrue(
        Long categoryId);

    // Search by name
    List<Product> findByNameContainingIgnoreCase(String name);

    // Price filter
    List<Product> findByPriceBetween(
        Double minPrice, Double maxPrice);

    // Brand filter
    List<Product> findByBrandAndIsActiveTrue(String brand);

    // Sorting by price
    List<Product> findByIsActiveTrueOrderByPriceAsc();
    List<Product> findByIsActiveTrueOrderByPriceDesc();

    // Rating filter
    List<Product> findByRatingGreaterThanEqual(Double rating);
    @Modifying
    @Query("UPDATE Product p SET p.stock = p.stock - :qty WHERE p.id = :id AND p.stock >= :qty")
    int decrementStock(@Param("id") Long id, @Param("qty") int qty);

    // Custom search query
    @Query("SELECT p FROM Product p WHERE " +
        "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
        "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
        "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
        "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
        "(:brand IS NULL OR p.brand = :brand) AND " +
        "p.isActive = true")
    List<Product> searchProducts(
        @Param("name") String name,
        @Param("categoryId") Long categoryId,
        @Param("minPrice") Double minPrice,
        @Param("maxPrice") Double maxPrice,
        @Param("brand") String brand);
    Page<Product> findByIsActiveTrue(Pageable pageable);
    Page<Product> findAll(Pageable pageable);
}

