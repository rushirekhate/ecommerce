package com.ecommerce.service.interfaces;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.Order.OrderStatus;
import java.util.List;

public interface IOrderService {

    // Place Order
    Order placeOrder(Long userId, Long addressId,
                    String paymentMethod,
                    String couponCode);

    // User
    List<Order> getOrdersByUserId(Long userId);
    Order getOrderById(Long id);
    Order getOrderByOrderNumber(String orderNumber);
    void cancelOrder(Long orderId, Long userId);

    // Admin
    List<Order> getAllOrders();
    List<Order> getOrdersByStatus(OrderStatus status);
    Order updateOrderStatus(Long orderId, 
                           OrderStatus status);

    // Revenue
    Double getTotalRevenue();
    Long getOrderCountByStatus(OrderStatus status);
}