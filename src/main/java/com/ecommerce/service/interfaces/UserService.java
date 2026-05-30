package com.ecommerce.service.interfaces;

import com.ecommerce.entity.User;
import java.util.List;

public interface UserService {

    // Auth
    User register(User user);
    User login(String email, String password);
    void sendEmailOtp(String email);
    boolean verifyEmailOtp(String email, String otp);
    void forgotPassword(String email);
    void resetPassword(String email,
                      String otp,
                      String newPassword);

    // User CRUD
    User getUserById(Long id);
    User getUserByEmail(String email);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    List<User> getAllUsers();

    // Admin
    void blockUser(Long id);
    void unblockUser(Long id);
    List<User> getBlockedUsers();
}
