package com.ecommerce.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDto<T> {

    private boolean success;
    private String message;
    private T data;

    // Success response
    public static <T> ApiResponseDto<T> success(
            String message, T data) {
        return new ApiResponseDto<>(
            true, message, data);
    }

    // Error response
    public static <T> ApiResponseDto<T> error(
            String message) {
        return new ApiResponseDto<>(
            false, message, null);
    }
}