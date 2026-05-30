package com.ecommerce.service.impl;

import com.ecommerce.entity.*;
import com.ecommerce.entity.Order.*;
import com.ecommerce.repository.*;
import com.ecommerce.service.interfaces.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;

    @Override
    @Transactional
    public Order placeOrder(Long userId, Long addressId, String paymentMethod, String couponCode) {
        log.info("Placing order for userId: {}", userId);

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found!"));
        Address address = addressRepository.findById(addressId)
            .orElseThrow(() -> new RuntimeException("Address not found!"));
        List<Cart> cartItems = cartRepository.findByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty!");
        }

        double totalAmount = cartItems.stream().mapToDouble(cart -> {
            Double price = cart.getVariant() != null
                ? cart.getVariant().getPrice()
                : cart.getProduct().getPrice();
            return price * cart.getQuantity();
        }).sum();

        double discountAmount = 0.0;
        if (couponCode != null && !couponCode.isEmpty()) {
            Coupon coupon = couponRepository.findByCode(couponCode)
                .orElseThrow(() -> new RuntimeException("Invalid coupon!"));
            if (coupon.getDiscountType() == Coupon.DiscountType.PERCENTAGE) {
                discountAmount = totalAmount * coupon.getDiscountValue() / 100;
                if (coupon.getMaxDiscountAmount() != null) {
                    discountAmount = Math.min(discountAmount, coupon.getMaxDiscountAmount());
                }
            } else {
                discountAmount = coupon.getDiscountValue();
            }
        }

        double finalAmount = totalAmount - discountAmount;

        Order order = new Order();
        order.setUser(user);
        order.setOrderNumber(generateOrderNumber());
        order.setTotalAmount(totalAmount);
        order.setDiscountAmount(discountAmount);
        order.setFinalAmount(finalAmount);
        order.setStatus(OrderStatus.PENDING);
        order.setPaymentMethod(PaymentMethod.valueOf(paymentMethod));
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setAddressLine(address.getAddressLine1());
        order.setCity(address.getCity());
        order.setState(address.getState());
        order.setPincode(address.getPincode());
        order.setPhone(address.getPhone());

        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();
        for (Cart cart : cartItems) {
            OrderItem item = new OrderItem();
            item.setOrder(savedOrder);
            item.setProduct(cart.getProduct());
            item.setVariant(cart.getVariant());
            item.setQuantity(cart.getQuantity());

            Double price = cart.getVariant() != null
                ? cart.getVariant().getPrice()
                : cart.getProduct().getPrice();
            item.setPrice(price);
            item.setTotalPrice(price * cart.getQuantity());

            // Race condition safe stock update
            int updated = productRepository.decrementStock(
                cart.getProduct().getId(), cart.getQuantity());
            if (updated == 0) {
                log.error("Insufficient stock for product: {}", cart.getProduct().getName());
                throw new RuntimeException("Insufficient stock for: " + cart.getProduct().getName());
            }

            orderItems.add(item);
        }
        orderItemRepository.saveAll(orderItems);
        cartRepository.deleteByUserId(userId);

        log.info("Order placed successfully: {}", savedOrder.getOrderNumber());
        return savedOrder;
    }

    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis() + "-" +
            UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found!"));
    }

    @Override
    public Order getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber)
            .orElseThrow(() -> new RuntimeException("Order not found!"));
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId, Long userId) {
        Order order = getOrderById(orderId);
        if (!order.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized!");
        }
        if (order.getStatus() != OrderStatus.PENDING && order.getStatus() != OrderStatus.CONFIRMED) {
            throw new RuntimeException("Order cannot be cancelled!");
        }
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        log.info("Order cancelled: {}", orderId);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        log.info("Order {} status updated to {}", orderId, status);
        return orderRepository.save(order);
    }

    @Override
    public Double getTotalRevenue() {
        return orderRepository.getTotalRevenue();
    }

    @Override
    public Long getOrderCountByStatus(OrderStatus status) {
        return orderRepository.countByStatus(status);
    }
}