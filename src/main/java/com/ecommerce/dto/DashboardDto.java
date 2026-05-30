package com.ecommerce.dto;

import lombok.Data;

@Data
public class DashboardDto {

    // Orders
    private Long totalOrders;
    private Long pendingOrders;
    private Long deliveredOrders;
    private Long cancelledOrders;

    // Revenue
    private Double totalRevenue;
    private Double todayRevenue;
    private Double monthRevenue;

    // Users
    private Long totalUsers;
    private Long newUsersToday;

    // Products
    private Long totalProducts;
    private Long outOfStockProducts;

    // Support
    private Integer openTickets;
    private Integer pendingReturns;
}