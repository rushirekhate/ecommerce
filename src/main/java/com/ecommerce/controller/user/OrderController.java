package com.ecommerce.controller.user;

import com.ecommerce.dto.ApiResponseDto;
import com.ecommerce.dto.CheckoutDto;
import com.ecommerce.dto.OrderDto;
import com.ecommerce.dto.OrderItemDto;
import com.ecommerce.entity.Order;
import com.ecommerce.service.interfaces.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    // Place order
    @PostMapping("/place/{userId}")
    public ResponseEntity<ApiResponseDto<OrderDto>>
            placeOrder(
                @PathVariable Long userId,
                @RequestBody CheckoutDto dto) {
        Order order = orderService.placeOrder(
            userId,
            dto.getAddressId(),
            dto.getPaymentMethod(),
            dto.getCouponCode());
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Order placed successfully!",
                mapToDto(order)));
    }

    // Get all orders by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDto<List<OrderDto>>>
            getOrdersByUser(
                @PathVariable Long userId) {
        List<OrderDto> orders = orderService
            .getOrdersByUserId(userId).stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Orders fetched!", orders));
    }

    // Get order by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<OrderDto>>
            getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Order fetched!", mapToDto(order)));
    }

    // Get order by order number
    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<ApiResponseDto<OrderDto>>
            getOrderByNumber(
                @PathVariable String orderNumber) {
        Order order = orderService
            .getOrderByOrderNumber(orderNumber);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Order fetched!", mapToDto(order)));
    }

    // Cancel order
    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<ApiResponseDto<String>>
            cancelOrder(
                @PathVariable Long orderId,
                @RequestParam Long userId) {
        orderService.cancelOrder(orderId, userId);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Order cancelled!", null));
    }

    // Helper
    private OrderDto mapToDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setDiscountAmount(order.getDiscountAmount());
        dto.setFinalAmount(order.getFinalAmount());
        dto.setStatus(order.getStatus().toString());
        dto.setPaymentMethod(
            order.getPaymentMethod().toString());
        dto.setPaymentStatus(
            order.getPaymentStatus().toString());
        dto.setAddressLine(order.getAddressLine());
        dto.setCity(order.getCity());
        dto.setState(order.getState());
        dto.setPincode(order.getPincode());
        dto.setCreatedAt(order.getCreatedAt());

        if (order.getOrderItems() != null) {
            List<OrderItemDto> items = order
                .getOrderItems().stream()
                .map(item -> {
                    OrderItemDto itemDto = 
                        new OrderItemDto();
                    itemDto.setId(item.getId());
                    itemDto.setProductName(
                        item.getProduct().getName());
                    itemDto.setQuantity(
                        item.getQuantity());
                    itemDto.setPrice(item.getPrice());
                    itemDto.setTotalPrice(
                        item.getTotalPrice());
                    if (item.getVariant() != null) {
                        itemDto.setColor(
                            item.getVariant().getColor());
                        itemDto.setSize(
                            item.getVariant().getSize());
                    }
                    if (item.getProduct().getImages() 
                            != null && !item.getProduct()
                            .getImages().isEmpty()) {
                        itemDto.setProductImage(
                            item.getProduct()
                                .getImages().get(0)
                                .getImageUrl());
                    }
                    return itemDto;
                })
                .collect(Collectors.toList());
            dto.setOrderItems(items);
        }
        return dto;
    }
}