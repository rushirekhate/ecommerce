package com.ecommerce.service.interfaces;

import com.ecommerce.entity.ReturnRequest;
import com.ecommerce.entity.ReturnRequest.ReturnStatus;
import java.util.List;

public interface IReturnService {

    // User
    ReturnRequest createReturnRequest(Long userId,Long orderId,String reason, String description);

    List<ReturnRequest> getReturnsByUserId(Long userId);
    
    ReturnRequest getReturnById(Long id);

    // Admin
    List<ReturnRequest> getAllReturns();
    List<ReturnRequest> getReturnsByStatus( ReturnStatus status);
    
    ReturnRequest updateReturnStatus(Long id, ReturnStatus status,  String adminNote);

    void approveReturn(Long id, String adminNote);
    void rejectReturn(Long id, String adminNote);
    void processRefund(Long id);
}