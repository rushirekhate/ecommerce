package com.ecommerce.service.impl;

import com.ecommerce.entity.OtpLog;
import com.ecommerce.entity.OtpLog.OtpType;
import com.ecommerce.repository.OtpLogRepository;
import com.ecommerce.service.interfaces.IOtpService;
import com.ecommerce.util.EmailUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements IOtpService {

    private final OtpLogRepository otpLogRepository;
    private final EmailUtil emailUtil;

    // 5 minutes expiry
    private static final int OTP_EXPIRY_MINUTES = 5;

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    @Override
    @Transactional
    public void generateAndSendOtp(
            String email, OtpType type) {

        invalidateOtp(email, type);

        String otp = generateOtp();

        OtpLog otpLog = new OtpLog();
        otpLog.setEmail(email);
        otpLog.setOtp(otp);
        otpLog.setType(type);
        otpLog.setUsed(false);
        otpLog.setExpiryTime(LocalDateTime.now()
            .plusMinutes(OTP_EXPIRY_MINUTES));

        otpLogRepository.save(otpLog);
        emailUtil.sendOtpEmail(email, otp);

        // Console pe OTP print hoga testing ke liye
        System.out.println(
            "=============================");
        System.out.println(
            "OTP for: " + email);
        System.out.println(
            "OTP: " + otp);
        System.out.println(
            "=============================");
    }
    @Override
    public void generateAndSendSmsOtp(
            String phone, OtpType type) {

        String otp = generateOtp();

        OtpLog otpLog = new OtpLog();
        otpLog.setPhone(phone);
        otpLog.setOtp(otp);
        otpLog.setType(type);
        otpLog.setUsed(false);
        otpLog.setExpiryTime(LocalDateTime.now()
            .plusMinutes(OTP_EXPIRY_MINUTES));

        otpLogRepository.save(otpLog);

        // SMS send baad mein add karenge
        System.out.println(
            "OTP for " + phone + " : " + otp);
    }

    @Override
    public boolean verifyOtp(String email,
            String otp, OtpType type) {

        OtpLog otpLog = otpLogRepository
            .findTopByEmailAndTypeAndIsUsedFalseOrderByCreatedAtDesc(
                email, type)
            .orElseThrow(() -> new RuntimeException(
                "OTP not found!"));

        // Expiry check
        if (LocalDateTime.now()
                .isAfter(otpLog.getExpiryTime())) {
            throw new RuntimeException("OTP expired!");
        }

        // OTP match check
        if (!otpLog.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP!");
        }

        // Mark as used
        otpLog.setUsed(true);
        otpLogRepository.save(otpLog);

        return true;
    }

    @Override
    public boolean verifySmsOtp(String phone,
            String otp, OtpType type) {

        OtpLog otpLog = otpLogRepository
            .findTopByPhoneAndTypeAndIsUsedFalseOrderByCreatedAtDesc(
                phone, type)
            .orElseThrow(() -> new RuntimeException(
                "OTP not found!"));

        if (LocalDateTime.now()
                .isAfter(otpLog.getExpiryTime())) {
            throw new RuntimeException("OTP expired!");
        }

        if (!otpLog.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP!");
        }

        otpLog.setUsed(true);
        otpLogRepository.save(otpLog);

        return true;
    }

    @Override
    public void resendOtp(String email, OtpType type) {
        generateAndSendOtp(email, type);
    }

    @Override
    @Transactional
    public void invalidateOtp(
            String email, OtpType type) {
        otpLogRepository.deleteByEmailAndType(
            email, type);
    }
}
