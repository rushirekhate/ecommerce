package com.ecommerce.repository;

import com.ecommerce.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserCouponRepository 
    extends JpaRepository<UserCoupon, Long> {

    List<UserCoupon> findByUserId(Long userId);
    
    boolean existsByUserIdAndCouponId(
        Long userId, Long couponId);
    
    int countByUserIdAndCouponId(
        Long userId, Long couponId);
}