package com.ecommerce.dto;

import com.ecommerce.entity.Address.AddressType;
import lombok.Data;

@Data
public class AddressDto {

    private Long id;
    private String fullName;
    private String phone;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String pincode;
    private String landmark;
    private AddressType type;
    private boolean isDefault;
}