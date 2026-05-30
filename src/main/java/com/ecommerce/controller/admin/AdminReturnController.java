package com.ecommerce.controller.admin;

import com.ecommerce.dto.ApiResponseDto;
import com.ecommerce.entity.ReturnRequest;
import com.ecommerce.entity.ReturnRequest.ReturnStatus;
import com.ecommerce.service.interfaces.IReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/returns")
@RequiredArgsConstructor
public class AdminReturnController {

    private final IReturnService returnService;

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<ReturnRequest>>>
            getAllReturns() {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                returnService.getAllReturns()));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponseDto<List<ReturnRequest>>>
            getByStatus(
                @PathVariable ReturnStatus status) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                returnService
                    .getReturnsByStatus(status)));
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<ApiResponseDto<String>>
            approve(@PathVariable Long id,
                @RequestParam String adminNote) {
        returnService.approveReturn(id, adminNote);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Return approved!", null));
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<ApiResponseDto<String>>
            reject(@PathVariable Long id,
                @RequestParam String adminNote) {
        returnService.rejectReturn(id, adminNote);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Return rejected!", null));
    }

    @PutMapping("/refund/{id}")
    public ResponseEntity<ApiResponseDto<String>>
            processRefund(@PathVariable Long id) {
        returnService.processRefund(id);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Refund processed!", null));
    }
}