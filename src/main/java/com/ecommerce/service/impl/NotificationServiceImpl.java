package com.ecommerce.service.impl;

import com.ecommerce.entity.Notification;
import com.ecommerce.entity.Notification.NotificationType;
import com.ecommerce.entity.User;
import com.ecommerce.repository.NotificationRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.interfaces.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl 
    implements INotificationService {

    private final NotificationRepository 
        notificationRepository;
    private final UserRepository userRepository;

    @Override
    public Notification sendNotification(Long userId,
            String title, String message,
            String link, NotificationType type) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException(
                "User not found!"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setLink(link);
        notification.setType(type);
        notification.setRead(false);

        return notificationRepository
            .save(notification);
    }

    @Override
    public List<Notification> getNotificationsByUserId(
            Long userId) {
        return notificationRepository
            .findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public List<Notification> getUnreadNotifications(
            Long userId) {
        return notificationRepository
            .findByUserIdAndIsReadFalse(userId);
    }

    @Override
    public int getUnreadCount(Long userId) {
        return notificationRepository
            .countByUserIdAndIsReadFalse(userId);
    }

    @Override
    public void markAsRead(Long notificationId) {
        Notification notification = 
            notificationRepository
                .findById(notificationId)
                .orElseThrow(() -> new RuntimeException(
                    "Notification not found!"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public void markAllAsRead(Long userId) {
        notificationRepository.markAllAsRead(userId);
    }

    @Override
    public void deleteNotification(
            Long notificationId) {
        notificationRepository
            .deleteById(notificationId);
    }
}