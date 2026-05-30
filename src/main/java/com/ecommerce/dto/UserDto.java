package com.ecommerce.dto;

import com.ecommerce.entity.User.Role;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private Role role;
    private String profileImage;
    private String gender;
    private boolean isEmailVerified;
    private boolean isActive;
}