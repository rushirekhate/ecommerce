package com.ecommerce.controller.admin;

import com.ecommerce.dto.ApiResponseDto;
import com.ecommerce.entity.User;
import com.ecommerce.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<User>>>
            getAllUsers() {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                userService.getAllUsers()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<User>>
            getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                userService.getUserById(id)));
    }

    @PutMapping("/block/{id}")
    public ResponseEntity<ApiResponseDto<String>>
            blockUser(@PathVariable Long id) {
        userService.blockUser(id);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "User blocked!", null));
    }

    @PutMapping("/unblock/{id}")
    public ResponseEntity<ApiResponseDto<String>>
            unblockUser(@PathVariable Long id) {
        userService.unblockUser(id);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "User unblocked!", null));
    }

    @GetMapping("/blocked")
    public ResponseEntity<ApiResponseDto<List<User>>>
            getBlockedUsers() {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                userService.getBlockedUsers()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDto<String>>
            deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "User deleted!", null));
    }
}