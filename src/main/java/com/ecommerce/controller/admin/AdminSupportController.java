package com.ecommerce.controller.admin;

import com.ecommerce.dto.ApiResponseDto;
import com.ecommerce.entity.SupportTicket;
import com.ecommerce.entity.SupportTicket.TicketStatus;
import com.ecommerce.service.interfaces.ISupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/support")
@RequiredArgsConstructor
public class AdminSupportController {

    private final ISupportService supportService;

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<SupportTicket>>>
            getAllTickets() {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                supportService.getAllTickets()));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponseDto<List<SupportTicket>>>
            getByStatus(
                @PathVariable TicketStatus status) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                supportService
                    .getTicketsByStatus(status)));
    }

    @PutMapping("/reply/{ticketId}")
    public ResponseEntity<ApiResponseDto<SupportTicket>>
            reply(@PathVariable Long ticketId,
                @RequestParam String adminReply) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Replied!",
                supportService.replyToTicket(
                    ticketId, adminReply)));
    }

    @PutMapping("/status/{ticketId}")
    public ResponseEntity<ApiResponseDto<SupportTicket>>
            updateStatus(
                @PathVariable Long ticketId,
                @RequestParam TicketStatus status) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Updated!",
                supportService.updateTicketStatus(
                    ticketId, status)));
    }

    @GetMapping("/open-count")
    public ResponseEntity<ApiResponseDto<Integer>>
            getOpenCount() {
        return ResponseEntity.ok(
            ApiResponseDto.success("Count!",
                supportService.getOpenTicketsCount()));
    }
}