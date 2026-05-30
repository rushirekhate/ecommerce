package com.ecommerce.service.interfaces;

import com.ecommerce.entity.Notification;
import com.ecommerce.entity.Notification.NotificationType;
import java.util.List;

public interface INotificationService {

    Notification sendNotification(Long userId,
                                 String title,
                                 String message,
                                 String link,
                                 NotificationType type);

    List<Notification> getNotificationsByUserId(
        Long userId);

    List<Notification> getUnreadNotifications(
        Long userId);

    int getUnreadCount(Long userId);

    void markAsRead(Long notificationId);

    void markAllAsRead(Long userId);

    void deleteNotification(Long notificationId);
}
