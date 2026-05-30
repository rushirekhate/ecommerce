package com.ecommerce.service.impl;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.ReturnRequest;
import com.ecommerce.entity.ReturnRequest.ReturnStatus;
import com.ecommerce.entity.User;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ReturnRequestRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.interfaces.IReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReturnServiceImpl 
    implements IReturnService {

    private final ReturnRequestRepository 
        returnRequestRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    public ReturnRequest createReturnRequest(
            Long userId, Long orderId,
            String reason, String description) {

        // Already return requested check
        if (returnRequestRepository
                .existsByOrderId(orderId)) {
            throw new RuntimeException(
                "Return already requested!");
        }

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException(
                "User not found!"));

        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException(
                "Order not found!"));

        // Only delivered orders can be returned
        if (order.getStatus() != 
                Order.OrderStatus.DELIVERED) {
            throw new RuntimeException(
                "Only delivered orders can be returned!");
        }

        ReturnRequest returnRequest = new ReturnRequest();
        returnRequest.setUser(user);
        returnRequest.setOrder(order);
        returnRequest.setReason(reason);
        returnRequest.setDescription(description);
        returnRequest.setStatus(
            ReturnStatus.REQUESTED);

        return returnRequestRepository
            .save(returnRequest);
    }

    @Override
    public List<ReturnRequest> getReturnsByUserId(
            Long userId) {
        return returnRequestRepository
            .findByUserId(userId);
    }

    @Override
    public ReturnRequest getReturnById(Long id) {
        return returnRequestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(
                "Return request not found!"));
    }

    @Override
    public List<ReturnRequest> getAllReturns() {
        return returnRequestRepository.findAll();
    }

    @Override
    public List<ReturnRequest> getReturnsByStatus(
            ReturnStatus status) {
        return returnRequestRepository
            .findByStatus(status);
    }

    @Override
    public ReturnRequest updateReturnStatus(
            Long id, ReturnStatus status,
            String adminNote) {
        ReturnRequest returnRequest = 
            getReturnById(id);
        returnRequest.setStatus(status);
        returnRequest.setAdminNote(adminNote);
        return returnRequestRepository
            .save(returnRequest);
    }

    @Override
    public void approveReturn(
            Long id, String adminNote) {
        updateReturnStatus(
            id, ReturnStatus.APPROVED, adminNote);
    }

    @Override
    public void rejectReturn(
            Long id, String adminNote) {
        updateReturnStatus(
            id, ReturnStatus.REJECTED, adminNote);
    }

    @Override
    public void processRefund(Long id) {
        updateReturnStatus(
            id, ReturnStatus.REFUNDED, 
            "Refund processed!");
    }
}