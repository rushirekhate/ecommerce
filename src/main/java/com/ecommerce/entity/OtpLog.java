package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "otp_logs")
public class OtpLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String phone;
    private String otp;

    @Enumerated(EnumType.STRING)
    private OtpType type;

    private boolean isUsed = false;
    private LocalDateTime expiryTime;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum OtpType {
        EMAIL_VERIFY, LOGIN, FORGOT_PASSWORD, PHONE_VERIFY
    }
}