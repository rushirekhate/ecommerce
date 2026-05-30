package com.ecommerce.controller.admin;

import com.ecommerce.dto.ApiResponseDto;
import com.ecommerce.entity.Coupon;
import com.ecommerce.service.interfaces.ICouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/coupons")
@RequiredArgsConstructor
public class AdminCouponController {

    private final ICouponService couponService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponseDto<Coupon>>
            addCoupon(@RequestBody Coupon coupon) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Coupon added!",
                couponService.addCoupon(coupon)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponseDto<Coupon>>
            updateCoupon(@PathVariable Long id,
                @RequestBody Coupon coupon) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Coupon updated!",
                couponService.updateCoupon(
                    id, coupon)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDto<String>>
            deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Coupon deleted!", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<Coupon>>>
            getAllCoupons() {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                couponService.getAllCoupons()));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponseDto<List<Coupon>>>
            getActiveCoupons() {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                couponService.getActiveCoupons()));
    }
}