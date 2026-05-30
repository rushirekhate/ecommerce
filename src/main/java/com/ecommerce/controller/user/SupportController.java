package com.ecommerce.controller.user;

import com.ecommerce.dto.ApiResponseDto;
import com.ecommerce.entity.SupportTicket;
import com.ecommerce.entity.SupportTicket.Priority;
import com.ecommerce.service.interfaces.ISupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/support")
@RequiredArgsConstructor
public class SupportController {

    private final ISupportService supportService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto<SupportTicket>>
            createTicket(
                @RequestParam Long userId,
                @RequestParam String subject,
                @RequestParam String message,
                @RequestParam Priority priority) {
        SupportTicket ticket = supportService
            .createTicket(
                userId, subject, message, priority);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Ticket created!", ticket));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDto<List<SupportTicket>>>
            getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                supportService
                    .getTicketsByUserId(userId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<SupportTicket>>
            getById(@PathVariable Long id) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                supportService.getTicketById(id)));
    }
}