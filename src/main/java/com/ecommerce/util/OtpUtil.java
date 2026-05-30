package com.ecommerce.util;

import org.springframework.stereotype.Component;
import java.security.SecureRandom;

@Component
public class OtpUtil {

    private static final int OTP_LENGTH = 6;
    private static final SecureRandom random = 
        new SecureRandom();

    // Numeric OTP generate karo
    public String generateOtp() {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    // 4 digit OTP
    public String generateFourDigitOtp() {
        int otp = 1000 + random.nextInt(9000);
        return String.valueOf(otp);
    }

    // Alphanumeric OTP
    public String generateAlphanumericOtp() {
        String chars = 
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(
                chars.charAt(
                    random.nextInt(chars.length())));
        }
        return otp.toString();
    }
}