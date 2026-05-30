package com.ecommerce.service.impl;

import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.hibernate.query.Page;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {
        log.info("Adding product: {}", product.getName());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product updatedProduct) {
        Product product = getProductById(id);
        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setDiscountPrice(updatedProduct.getDiscountPrice());
        product.setBrand(updatedProduct.getBrand());
        product.setStock(updatedProduct.getStock());
        product.setFeatured(updatedProduct.isFeatured());
        product.setCategory(updatedProduct.getCategory());
        product.setSubCategory(updatedProduct.getSubCategory());
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found!"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findByIsActiveTrue();
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryIdAndIsActiveTrue(categoryId);
    }

    @Override
    public List<Product> getProductsBySubCategory(Long subCategoryId) {
        return productRepository.findBySubCategoryId(subCategoryId);
    }

    @Override
    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Product> filterProducts(Long categoryId, Double minPrice, Double maxPrice, String brand) {
        return productRepository.searchProducts(null, categoryId, minPrice, maxPrice, brand);
    }

    @Override
    public List<Product> getProductsSortedByPriceAsc() {
        return productRepository.findByIsActiveTrueOrderByPriceAsc();
    }

    @Override
    public List<Product> getProductsSortedByPriceDesc() {
        return productRepository.findByIsActiveTrueOrderByPriceDesc();
    }

    @Override
    public List<Product> getProductsSortedByRating() {
        return productRepository.findByRatingGreaterThanEqual(0.0);
    }

    @Override
    public List<Product> getFeaturedProducts() {
        return productRepository.findByIsFeaturedTrue();
    }

    @Override
    public void activateProduct(Long id) {
        Product product = getProductById(id);
        product.setActive(true);
        productRepository.save(product);
    }

    @Override
    public void deactivateProduct(Long id) {
        Product product = getProductById(id);
        product.setActive(false);
        productRepository.save(product);
    }

    @Override
    public void updateStock(Long id, Integer stock) {
        Product product = getProductById(id);
        product.setStock(stock);
        productRepository.save(product);
    }
   
}