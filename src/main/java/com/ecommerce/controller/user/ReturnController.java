package com.ecommerce.controller.user;

import com.ecommerce.dto.ApiResponseDto;
import com.ecommerce.entity.ReturnRequest;
import com.ecommerce.service.interfaces.IReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/returns")
@RequiredArgsConstructor
public class ReturnController {

    private final IReturnService returnService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto<ReturnRequest>>
            createReturn(
                @RequestParam Long userId,
                @RequestParam Long orderId,
                @RequestParam String reason,
                @RequestParam String description) {
        ReturnRequest returnRequest = returnService
            .createReturnRequest(
                userId, orderId, reason, description);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Return requested!", returnRequest));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDto<List<ReturnRequest>>>
            getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                returnService
                    .getReturnsByUserId(userId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ReturnRequest>>
            getById(@PathVariable Long id) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                returnService.getReturnById(id)));
    }
}