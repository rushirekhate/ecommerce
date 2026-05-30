package com.ecommerce.controller.admin;

import com.ecommerce.dto.ApiResponseDto;
import com.ecommerce.entity.Product;
import com.ecommerce.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponseDto<Product>>
            addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Product added!",
                productService.addProduct(product)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponseDto<Product>>
            updateProduct(@PathVariable Long id,
                @RequestBody Product product) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Product updated!",
                productService.updateProduct(
                    id, product)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDto<String>>
            deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Product deleted!", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<Product>>>
            getAllProducts() {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                productService.getAllProducts()));
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<ApiResponseDto<String>>
            activate(@PathVariable Long id) {
        productService.activateProduct(id);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Product activated!", null));
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<ApiResponseDto<String>>
            deactivate(@PathVariable Long id) {
        productService.deactivateProduct(id);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Product deactivated!", null));
    }

    @PutMapping("/stock/{id}")
    public ResponseEntity<ApiResponseDto<String>>
            updateStock(@PathVariable Long id,
                @RequestParam Integer stock) {
        productService.updateStock(id, stock);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Stock updated!", null));
    }
}