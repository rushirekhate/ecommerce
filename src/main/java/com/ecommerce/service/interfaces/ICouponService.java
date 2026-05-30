package com.ecommerce.service.interfaces;

import com.ecommerce.entity.Coupon;
import java.util.List;

public interface ICouponService {

    // Admin CRUD
    Coupon addCoupon(Coupon coupon);
    Coupon updateCoupon(Long id, Coupon coupon);
    void deleteCoupon(Long id);
    Coupon getCouponById(Long id);
    List<Coupon> getAllCoupons();
    List<Coupon> getActiveCoupons();

    // User
    Coupon validateCoupon(String code, 
                         Long userId,
                         Double orderAmount);
    
    Double applyCoupon(String code,
                      Long userId,
                      Double orderAmount);
    
    void markCouponAsUsed(String code,
                         Long userId,
                         Long orderId);

    boolean isCouponValidForUser(String code,
                                Long userId);
}
