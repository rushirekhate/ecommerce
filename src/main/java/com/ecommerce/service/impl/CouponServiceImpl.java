package com.ecommerce.service.impl;

import com.ecommerce.entity.Coupon;
import com.ecommerce.entity.UserCoupon;
import com.ecommerce.entity.User;
import com.ecommerce.entity.Order;
import com.ecommerce.repository.CouponRepository;
import com.ecommerce.repository.UserCouponRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.service.interfaces.ICouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl 
    implements ICouponService {

    private final CouponRepository couponRepository;
    private final UserCouponRepository 
        userCouponRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    public Coupon addCoupon(Coupon coupon) {
        if (couponRepository.existsByCode(
                coupon.getCode())) {
            throw new RuntimeException(
                "Coupon code already exists!");
        }
        return couponRepository.save(coupon);
    }

    @Override
    public Coupon updateCoupon(
            Long id, Coupon updated) {
        Coupon coupon = getCouponById(id);
        coupon.setCode(updated.getCode());
        coupon.setDescription(updated.getDescription());
        coupon.setDiscountType(updated.getDiscountType());
        coupon.setDiscountValue(updated.getDiscountValue());
        coupon.setMinOrderAmount(
            updated.getMinOrderAmount());
        coupon.setMaxDiscountAmount(
            updated.getMaxDiscountAmount());
        coupon.setTotalUsageLimit(
            updated.getTotalUsageLimit());
        coupon.setPerUserLimit(updated.getPerUserLimit());
        coupon.setStartDate(updated.getStartDate());
        coupon.setExpiryDate(updated.getExpiryDate());
        return couponRepository.save(coupon);
    }

    @Override
    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }

    @Override
    public Coupon getCouponById(Long id) {
        return couponRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(
                "Coupon not found!"));
    }

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    public List<Coupon> getActiveCoupons() {
        return couponRepository.findByIsActiveTrue();
    }

    @Override
    public Coupon validateCoupon(String code,
            Long userId, Double orderAmount) {

        Coupon coupon = couponRepository
            .findValidCouponByCode(
                code, LocalDateTime.now(), orderAmount)
            .orElseThrow(() -> new RuntimeException(
                "Invalid or expired coupon!"));

        // Check total usage limit
        if (coupon.getTotalUsageLimit() != null &&
            coupon.getUsedCount() >= 
                coupon.getTotalUsageLimit()) {
            throw new RuntimeException(
                "Coupon usage limit exceeded!");
        }

        // Check per user limit
        int userUsageCount = userCouponRepository
            .countByUserIdAndCouponId(
                userId, coupon.getId());
        if (userUsageCount >= coupon.getPerUserLimit()) {
            throw new RuntimeException(
                "You have already used this coupon!");
        }

        return coupon;
    }

    @Override
    public Double applyCoupon(String code,
            Long userId, Double orderAmount) {
        Coupon coupon = validateCoupon(
            code, userId, orderAmount);

        double discount = 0.0;
        if (coupon.getDiscountType() == 
                Coupon.DiscountType.PERCENTAGE) {
            discount = orderAmount * 
                coupon.getDiscountValue() / 100;
            if (coupon.getMaxDiscountAmount() != null) {
                discount = Math.min(discount,
                    coupon.getMaxDiscountAmount());
            }
        } else {
            discount = coupon.getDiscountValue();
        }

        return discount;
    }

    @Override
    public void markCouponAsUsed(String code,
            Long userId, Long orderId) {
        Coupon coupon = couponRepository
            .findByCode(code)
            .orElseThrow(() -> new RuntimeException(
                "Coupon not found!"));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException(
                "User not found!"));

        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException(
                "Order not found!"));

        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUser(user);
        userCoupon.setCoupon(coupon);
        userCoupon.setOrder(order);
        userCouponRepository.save(userCoupon);

        // Increment used count
        coupon.setUsedCount(coupon.getUsedCount() + 1);
        couponRepository.save(coupon);
    }

    @Override
    public boolean isCouponValidForUser(
            String code, Long userId) {
        try {
            Coupon coupon = couponRepository
                .findByCode(code)
                .orElseThrow(() -> 
                    new RuntimeException(
                        "Coupon not found!"));
            int userUsageCount = userCouponRepository
                .countByUserIdAndCouponId(
                    userId, coupon.getId());
            return userUsageCount < 
                coupon.getPerUserLimit();
        } catch (Exception e) {
            return false;
        }
    }
}
