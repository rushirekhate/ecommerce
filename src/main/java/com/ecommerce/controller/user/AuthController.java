package com.ecommerce.controller.user;
import com.ecommerce.util.JwtUtil;

import jakarta.validation.Valid;

import com.ecommerce.dto.*;
import com.ecommerce.entity.User;
import com.ecommerce.service.interfaces.IOtpService;
import com.ecommerce.service.interfaces.UserService;
import com.ecommerce.entity.OtpLog.OtpType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final IOtpService otpService;
    private final JwtUtil jwtUtil;

    // Register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPhone(dto.getPhone());
        User saved = userService.register(user);
        UserDto userDto = mapToDto(saved);

        // Send OTP
        otpService.generateAndSendOtp(
            saved.getEmail(),
            OtpType.EMAIL_VERIFY);

        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Registration successful! "
                + "Please verify email.", userDto));
    }

    // Login
//    @PostMapping("/login")
//    public ResponseEntity<ApiResponseDto<UserDto>>
//            login(@RequestBody LoginDto dto) {
//        User user = userService.login(  dto.getEmail(), dto.getPassword());
//        UserDto userDto = mapToDto(user);
//        return ResponseEntity.ok(
//            ApiResponseDto.success(
//                "Login successful!", userDto));
//    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<Map<String, Object>>>
            login(@RequestBody LoginDto dto) {
        
        User user = userService.login(
            dto.getEmail(), dto.getPassword());

        // JWT Token generate karo
        String token = jwtUtil.generateToken(
            user.getEmail(),
            user.getRole().toString(),
            user.getId());

        // Response mein user + token bhejo
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("id", user.getId());
        response.put("name", user.getName());
        response.put("email", user.getEmail());
        response.put("role", user.getRole().toString());
        response.put("profileImage", 
            user.getProfileImage());

        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Login successful!", response));
    }

    // Send OTP
    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponseDto<String>>
            sendOtp(@RequestParam String email) {
        otpService.generateAndSendOtp(
            email, OtpType.EMAIL_VERIFY);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "OTP sent successfully!", null));
    }

    // Verify OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponseDto<String>>
            verifyOtp(@RequestBody OtpDto dto) {
        boolean verified = otpService.verifyOtp(
            dto.getEmail(), dto.getOtp(),
            OtpType.EMAIL_VERIFY);
        if (verified) {
            userService.verifyEmailOtp(
                dto.getEmail(), dto.getOtp());
        }
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Email verified successfully!", null));
    }

    // Forgot Password
    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponseDto<String>>
            forgotPassword(
                @RequestParam String email) {
        otpService.generateAndSendOtp(
            email, OtpType.FORGOT_PASSWORD);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "OTP sent to email!", null));
    }

    // Reset Password
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponseDto<String>>
            resetPassword(
                @RequestBody PasswordResetDto dto) {
        if (!dto.getNewPassword().equals(
                dto.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(
                ApiResponseDto.error(
                    "Passwords do not match!"));
        }
        userService.resetPassword(
            dto.getEmail(), dto.getOtp(),
            dto.getNewPassword());
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Password reset successful!", null));
    }

    // Resend OTP
    @PostMapping("/resend-otp")
    public ResponseEntity<ApiResponseDto<String>>
            resendOtp(@RequestParam String email,
                     @RequestParam String type) {
        otpService.resendOtp(
            email, OtpType.valueOf(type));
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "OTP resent successfully!", null));
    }

    // Helper method
    private UserDto mapToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        dto.setProfileImage(user.getProfileImage());
        dto.setGender(user.getGender());
        dto.setEmailVerified(user.isEmailVerified());
        dto.setActive(user.isActive());
        return dto;
    }
}