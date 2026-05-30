package com.ecommerce.service.impl;

import com.ecommerce.entity.OtpLog;
import com.ecommerce.entity.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.interfaces.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpServiceImpl otpService;

    @Override
    @Transactional
    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(User.Role.USER);
        return userRepository.save(user);
    }

    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found!"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }
        if (!user.isActive()) {
            throw new RuntimeException("Account is blocked!");
        }
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        User user = getUserById(id);
        user.setName(updatedUser.getName());
        user.setPhone(updatedUser.getPhone());
        user.setGender(updatedUser.getGender());
        user.setProfileImage(updatedUser.getProfileImage());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void blockUser(Long id) {
        User user = getUserById(id);
        user.setBlocked(true);
        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public void unblockUser(Long id) {
        User user = getUserById(id);
        user.setBlocked(false);
        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    public List<User> getBlockedUsers() {
        return userRepository.findAll().stream()
            .filter(User::isBlocked)
            .toList();
    }

    @Override
    public void sendEmailOtp(String email) {
        otpService.generateAndSendOtp(email, OtpLog.OtpType.EMAIL_VERIFY);
    }

    @Override
    public boolean verifyEmailOtp(String email, String otp) {
        return otpService.verifyOtp(email, otp, OtpLog.OtpType.EMAIL_VERIFY);
    }

    @Override
    public void forgotPassword(String email) {
        userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Email not found"));

        otpService.generateAndSendOtp(email, OtpLog.OtpType.FORGOT_PASSWORD);
    }

    @Override
    public void resetPassword(String email, String otp, String newPassword) {
        otpService.verifyOtp(email, otp, OtpLog.OtpType.FORGOT_PASSWORD);

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}