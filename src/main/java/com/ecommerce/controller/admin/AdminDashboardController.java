package com.ecommerce.controller.admin;


import com.ecommerce.dto.ApiResponseDto;
import com.ecommerce.dto.DashboardDto;
import com.ecommerce.entity.Order.OrderStatus;
import com.ecommerce.service.interfaces.IOrderService;
import com.ecommerce.service.interfaces.ISupportService;
import com.ecommerce.service.interfaces.UserService;
import com.ecommerce.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final IOrderService orderService;
    private final UserService userService;
    private final ProductService productService;
    private final ISupportService supportService;

    @GetMapping
    public ResponseEntity<ApiResponseDto<DashboardDto>>
            getDashboard() {
        DashboardDto dto = new DashboardDto();

        // Orders
        dto.setTotalOrders(
            orderService.getOrderCountByStatus(
                OrderStatus.PENDING) +
            orderService.getOrderCountByStatus(
                OrderStatus.DELIVERED) +
            orderService.getOrderCountByStatus(
                OrderStatus.CANCELLED));
        dto.setPendingOrders(
            orderService.getOrderCountByStatus(
                OrderStatus.PENDING));
        dto.setDeliveredOrders(
            orderService.getOrderCountByStatus(
                OrderStatus.DELIVERED));
        dto.setCancelledOrders(
            orderService.getOrderCountByStatus(
                OrderStatus.CANCELLED));

        // Revenue
        dto.setTotalRevenue(
            orderService.getTotalRevenue());

        // Users
        dto.setTotalUsers(
            (long) userService
                .getAllUsers().size());

        // Products
        dto.setTotalProducts(
            (long) productService
                .getAllProducts().size());

        // Support
        dto.setOpenTickets(
            supportService.getOpenTicketsCount());

        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Dashboard fetched!", dto));
    }
}