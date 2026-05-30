package com.ecommerce.controller.user;

import com.ecommerce.dto.ApiResponseDto;
import com.ecommerce.dto.ProductDto;
import com.ecommerce.entity.Product;
import com.ecommerce.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // Get all products
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<ProductDto>>>
            getAllProducts() {
        List<ProductDto> products = productService
            .getAllProducts().stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Products fetched!", products));
    }

    // Get product by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ProductDto>>
            getProductById(@PathVariable Long id) {
        Product product = 
            productService.getProductById(id);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Product fetched!",
                mapToDto(product)));
    }

    // Get by category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponseDto<List<ProductDto>>>
            getByCategory(
                @PathVariable Long categoryId) {
        List<ProductDto> products = productService
            .getProductsByCategory(categoryId)
            .stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Products fetched!", products));
    }

    // Get by subcategory
    @GetMapping("/subcategory/{subCategoryId}")
    public ResponseEntity<ApiResponseDto<List<ProductDto>>>
            getBySubCategory(
                @PathVariable Long subCategoryId) {
        List<ProductDto> products = productService
            .getProductsBySubCategory(subCategoryId)
            .stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Products fetched!", products));
    }

    // Search products
    @GetMapping("/search")
    public ResponseEntity<ApiResponseDto<List<ProductDto>>>
            searchProducts(
                @RequestParam String name) {
        List<ProductDto> products = productService
            .searchProducts(name).stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Products fetched!", products));
    }

    // Filter products
    @GetMapping("/filter")
    public ResponseEntity<ApiResponseDto<List<ProductDto>>>
            filterProducts(
                @RequestParam(required = false) 
                    Long categoryId,
                @RequestParam(required = false) 
                    Double minPrice,
                @RequestParam(required = false) 
                    Double maxPrice,
                @RequestParam(required = false) 
                    String brand) {
        List<ProductDto> products = productService
            .filterProducts(
                categoryId, minPrice,
                maxPrice, brand)
            .stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Products fetched!", products));
    }

    // Sort by price asc
    @GetMapping("/sort/price-asc")
    public ResponseEntity<ApiResponseDto<List<ProductDto>>>
            sortByPriceAsc() {
        List<ProductDto> products = productService
            .getProductsSortedByPriceAsc().stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Products fetched!", products));
    }

    // Sort by price desc
    @GetMapping("/sort/price-desc")
    public ResponseEntity<ApiResponseDto<List<ProductDto>>>
            sortByPriceDesc() {
        List<ProductDto> products = productService
            .getProductsSortedByPriceDesc().stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Products fetched!", products));
    }

    // Featured products
    @GetMapping("/featured")
    public ResponseEntity<ApiResponseDto<List<ProductDto>>>
            getFeaturedProducts() {
        List<ProductDto> products = productService
            .getFeaturedProducts().stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Featured products fetched!",
                products));
    }

    // Helper method
    private ProductDto mapToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setDiscountPrice(
            product.getDiscountPrice());
        dto.setBrand(product.getBrand());
        dto.setStock(product.getStock());
        dto.setRating(product.getRating());
        dto.setTotalReviews(product.getTotalReviews());
        dto.setActive(product.isActive());
        dto.setFeatured(product.isFeatured());
        if (product.getCategory() != null) {
            dto.setCategoryName(
                product.getCategory().getName());
        }
        if (product.getSubCategory() != null) {
            dto.setSubCategoryName(
                product.getSubCategory().getName());
        }
        return dto;
    }
}