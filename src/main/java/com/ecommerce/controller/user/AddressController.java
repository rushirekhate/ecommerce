package com.ecommerce.controller.user;

import com.ecommerce.dto.AddressDto;
import com.ecommerce.dto.ApiResponseDto;
import com.ecommerce.entity.Address;
import com.ecommerce.service.interfaces.IAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {

    private final IAddressService addressService;

    // Add address
    @PostMapping("/add/{userId}")
    public ResponseEntity<ApiResponseDto<AddressDto>>
            addAddress(
                @PathVariable Long userId,
                @RequestBody AddressDto dto) {
        Address address = mapToEntity(dto);
        Address saved = addressService
            .addAddress(userId, address);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Address added!", mapToDto(saved)));
    }

    // Get all addresses
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDto<List<AddressDto>>>
            getAddresses(@PathVariable Long userId) {
        List<AddressDto> addresses = addressService
            .getAddressesByUserId(userId).stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Addresses fetched!", addresses));
    }

    // Get default address
    @GetMapping("/default/{userId}")
    public ResponseEntity<ApiResponseDto<AddressDto>>
            getDefaultAddress(
                @PathVariable Long userId) {
        Address address = addressService
            .getDefaultAddress(userId);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Default address fetched!",
                mapToDto(address)));
    }

    // Update address
    @PutMapping("/update/{addressId}")
    public ResponseEntity<ApiResponseDto<AddressDto>>
            updateAddress(
                @PathVariable Long addressId,
                @RequestBody AddressDto dto) {
        Address updated = addressService
            .updateAddress(addressId, mapToEntity(dto));
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Address updated!", mapToDto(updated)));
    }

    // Delete address
    @DeleteMapping("/delete/{addressId}")
    public ResponseEntity<ApiResponseDto<String>>
            deleteAddress(
                @PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Address deleted!", null));
    }

    // Set default address
    @PutMapping("/set-default")
    public ResponseEntity<ApiResponseDto<String>>
            setDefault(
                @RequestParam Long userId,
                @RequestParam Long addressId) {
        addressService.setDefaultAddress(
            userId, addressId);
        return ResponseEntity.ok(
            ApiResponseDto.success(
                "Default address set!", null));
    }

    // Helper mapToDto
    private AddressDto mapToDto(Address address) {
        AddressDto dto = new AddressDto();
        dto.setId(address.getId());
        dto.setFullName(address.getFullName());
        dto.setPhone(address.getPhone());
        dto.setAddressLine1(address.getAddressLine1());
        dto.setAddressLine2(address.getAddressLine2());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setPincode(address.getPincode());
        dto.setLandmark(address.getLandmark());
        dto.setType(address.getType());
        dto.setDefault(address.isDefault());
        return dto;
    }

    // Helper mapToEntity
    private Address mapToEntity(AddressDto dto) {
        Address address = new Address();
        address.setFullName(dto.getFullName());
        address.setPhone(dto.getPhone());
        address.setAddressLine1(dto.getAddressLine1());
        address.setAddressLine2(dto.getAddressLine2());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setPincode(dto.getPincode());
        address.setLandmark(dto.getLandmark());
        address.setType(dto.getType());
        return address;
    }
}