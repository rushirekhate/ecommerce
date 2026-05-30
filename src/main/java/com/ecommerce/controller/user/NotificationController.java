package com.ecommerce.controller.user;

import com.ecommerce.dto.ApiResponseDto;
import com.ecommerce.entity.Notification;
import com.ecommerce.service.interfaces.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final INotificationService 
        notificationService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponseDto<List<Notification>>>
            getNotifications(
                @PathVariable Long userId) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                notificationService
                    .getNotificationsByUserId(userId)));
    }

    @GetMapping("/unread/{userId}")
    public ResponseEntity<ApiResponseDto<List<Notification>>>
            getUnread(@PathVariable Long userId) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                notificationService
                    .getUnreadNotifications(userId)));
    }

    @GetMapping("/count/{userId}")
    public ResponseEntity<ApiResponseDto<Integer>>
            getUnreadCount(@PathVariable Long userId) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Count!",
                notificationService
                    .getUnreadCount(userId)));
    }

    @PutMapping("/read/{notificationId}")
    public ResponseEntity<ApiResponseDto<String>>
            markAsRead(
                @PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Marked as read!", null));
    }

    @PutMapping("/read-all/{userId}")
    public ResponseEntity<ApiResponseDto<String>>
            markAllAsRead(@PathVariable Long userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "All marked as read!", null));
    }

    @DeleteMapping("/delete/{notificationId}")
    public ResponseEntity<ApiResponseDto<String>>
            delete(
                @PathVariable Long notificationId) {
        notificationService
            .deleteNotification(notificationId);
        return ResponseEntity.ok(
            ApiResponseDto.success("Deleted!", null));
    }
}