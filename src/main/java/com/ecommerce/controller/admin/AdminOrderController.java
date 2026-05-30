package com.ecommerce.controller.admin;

import com.ecommerce.dto.ApiResponseDto;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.Order.OrderStatus;
import com.ecommerce.service.interfaces.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final IOrderService orderService;

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<Order>>>
            getAllOrders() {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                orderService.getAllOrders()));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponseDto<List<Order>>>
            getByStatus(
                @PathVariable OrderStatus status) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                orderService
                    .getOrdersByStatus(status)));
    }

    @PutMapping("/update-status/{orderId}")
    public ResponseEntity<ApiResponseDto<Order>>
            updateStatus(
                @PathVariable Long orderId,
                @RequestParam OrderStatus status) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Status updated!",
                orderService.updateOrderStatus(
                    orderId, status)));
    }

    @GetMapping("/revenue")
    public ResponseEntity<ApiResponseDto<Double>>
            getTotalRevenue() {
        return ResponseEntity.ok(
            ApiResponseDto.success("Revenue fetched!",
                orderService.getTotalRevenue()));
    }

    @GetMapping("/count/{status}")
    public ResponseEntity<ApiResponseDto<Long>>
            getOrderCount(
                @PathVariable OrderStatus status) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Count fetched!",
                orderService
                    .getOrderCountByStatus(status)));
    }
}