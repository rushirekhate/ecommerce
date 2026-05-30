package com.ecommerce.service.interfaces;

import com.ecommerce.entity.Product;
import java.util.List;

public interface ProductService {

    // CRUD
    Product addProduct(Product product);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
    Product getProductById(Long id);
    List<Product> getAllProducts();

    // Filter & Search
    List<Product> getProductsByCategory(Long categoryId);
    List<Product> getProductsBySubCategory(
        Long subCategoryId);
    List<Product> searchProducts(String name);
    List<Product> filterProducts(
        Long categoryId,
        Double minPrice,
        Double maxPrice,
        String brand);

    // Sorting
    List<Product> getProductsSortedByPriceAsc();
    List<Product> getProductsSortedByPriceDesc();
    List<Product> getProductsSortedByRating();

    // Featured
    List<Product> getFeaturedProducts();

    // Admin
    void activateProduct(Long id);
    void deactivateProduct(Long id);
    void updateStock(Long id, Integer stock);
}
