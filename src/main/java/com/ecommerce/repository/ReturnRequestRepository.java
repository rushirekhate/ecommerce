package com.ecommerce.repository;

import com.ecommerce.entity.ReturnRequest;
import com.ecommerce.entity.ReturnRequest.ReturnStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReturnRequestRepository 
    extends JpaRepository<ReturnRequest, Long> {

    List<ReturnRequest> findByUserId(Long userId);
    
    List<ReturnRequest> findByOrderId(Long orderId);
    
    List<ReturnRequest> findByStatus(ReturnStatus status);
    
    boolean existsByOrderId(Long orderId);
}