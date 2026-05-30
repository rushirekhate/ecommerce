package com.ecommerce.repository;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.Order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository 
    extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);
    
    Optional<Order> findByOrderNumber(String orderNumber);
    
    List<Order> findByStatus(OrderStatus status);
    
    List<Order> findByUserIdOrderByCreatedAtDesc(
        Long userId);

    // Admin — orders by date range
    List<Order> findByCreatedAtBetween(
        LocalDateTime start, LocalDateTime end);

    // Total revenue
    @Query("SELECT SUM(o.finalAmount) FROM Order o " +
        "WHERE o.status = 'DELIVERED'")
    Double getTotalRevenue();

    // Orders count by status
    @Query("SELECT COUNT(o) FROM Order o " +
        "WHERE o.status = :status")
    Long countByStatus(@Param("status") OrderStatus status);
}