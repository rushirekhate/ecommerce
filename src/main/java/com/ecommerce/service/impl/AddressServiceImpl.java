package com.ecommerce.service.impl;

import com.ecommerce.entity.Address;
import com.ecommerce.entity.User;
import com.ecommerce.repository.AddressRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.interfaces.IAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements IAddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public Address addAddress(Long userId, Address address) {
        log.info("Adding address for userId: {}", userId);
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found!"));
        address.setUser(user);
        if (addressRepository.countByUserId(userId) == 0) {
            address.setDefault(true);
        }
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Long addressId, Address updated) {
        Address address = getAddressById(addressId);
        address.setFullName(updated.getFullName());
        address.setPhone(updated.getPhone());
        address.setAddressLine1(updated.getAddressLine1());
        address.setAddressLine2(updated.getAddressLine2());
        address.setCity(updated.getCity());
        address.setState(updated.getState());
        address.setPincode(updated.getPincode());
        address.setLandmark(updated.getLandmark());
        address.setType(updated.getType());
        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }

    @Override
    public Address getAddressById(Long addressId) {
        return addressRepository.findById(addressId)
            .orElseThrow(() -> new RuntimeException("Address not found!"));
    }

    @Override
    public List<Address> getAddressesByUserId(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    @Override
    public Address getDefaultAddress(Long userId) {
        return addressRepository.findByUserIdAndIsDefaultTrue(userId)
            .orElseThrow(() -> new RuntimeException("No default address found!"));
    }

    @Override
    public void setDefaultAddress(Long userId, Long addressId) {
        List<Address> addresses = addressRepository.findByUserId(userId);
        addresses.forEach(a -> a.setDefault(false));
        addressRepository.saveAll(addresses);
        Address address = getAddressById(addressId);
        address.setDefault(true);
        addressRepository.save(address);
    }
}