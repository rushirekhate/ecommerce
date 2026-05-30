package com.ecommerce.repository;

import com.ecommerce.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository 
    extends JpaRepository<Coupon, Long> {

    Optional<Coupon> findByCode(String code);
    
    boolean existsByCode(String code);
    
    List<Coupon> findByIsActiveTrue();

    // Valid coupons — not expired
    @Query("SELECT c FROM Coupon c WHERE " +
        "c.isActive = true AND " +
        "c.startDate <= :now AND " +
        "c.expiryDate >= :now")
    List<Coupon> findValidCoupons(
        @Param("now") LocalDateTime now);

    // Check coupon valid for order amount
    @Query("SELECT c FROM Coupon c WHERE " +
        "c.code = :code AND " +
        "c.isActive = true AND " +
        "c.expiryDate >= :now AND " +
        "c.minOrderAmount <= :orderAmount")
    Optional<Coupon> findValidCouponByCode(
        @Param("code") String code,
        @Param("now") LocalDateTime now,
        @Param("orderAmount") Double orderAmount);
}