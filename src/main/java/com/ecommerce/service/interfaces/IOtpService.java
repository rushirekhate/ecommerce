package com.ecommerce.service.interfaces;

import com.ecommerce.entity.OtpLog.OtpType;

public interface IOtpService {

    // Generate & Send
    void generateAndSendOtp(String email,  OtpType type);
    
    void generateAndSendSmsOtp(String phone,    OtpType type);

    // Verify
    boolean verifyOtp(String email, String otp,  OtpType type);
    
    boolean verifySmsOtp(String phone,  String otp, OtpType type);

    // Resend
    void resendOtp(String email, OtpType type);

    // Invalidate
    void invalidateOtp(String email, OtpType type);
}