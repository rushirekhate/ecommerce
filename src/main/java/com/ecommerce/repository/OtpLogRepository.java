package com.ecommerce.repository;

import com.ecommerce.entity.OtpLog;
import com.ecommerce.entity.OtpLog.OtpType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OtpLogRepository 
    extends JpaRepository<OtpLog, Long> {

    Optional<OtpLog> findTopByEmailAndTypeAndIsUsedFalseOrderByCreatedAtDesc(
        String email, OtpType type);
    
    Optional<OtpLog> findTopByPhoneAndTypeAndIsUsedFalseOrderByCreatedAtDesc(
        String phone, OtpType type);
    
    void deleteByEmailAndType(
        String email, OtpType type);
}
