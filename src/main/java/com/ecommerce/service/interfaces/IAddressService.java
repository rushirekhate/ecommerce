package com.ecommerce.service.interfaces;

import com.ecommerce.entity.Address;
import java.util.List;

public interface IAddressService {

    Address addAddress(Long userId, Address address);
    
    Address updateAddress(Long addressId, 
                         Address address);
    
    void deleteAddress(Long addressId);
    
    Address getAddressById(Long addressId);
    
    List<Address> getAddressesByUserId(Long userId);
    
    Address getDefaultAddress(Long userId);
    
    void setDefaultAddress(Long userId, Long addressId);
}