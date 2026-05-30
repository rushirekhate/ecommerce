package com.ecommerce.util;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class OrderNumberUtil {

    // ORD-20260414-XXXX
    public String generateOrderNumber() {
        String date = LocalDateTime.now()
            .format(DateTimeFormatter
                .ofPattern("yyyyMMdd"));
        String random = UUID.randomUUID()
            .toString()
            .substring(0, 6)
            .toUpperCase();
        return "ORD-" + date + "-" + random;
    }

    // INV-20260414-XXXX
    public String generateInvoiceNumber() {
        String date = LocalDateTime.now()
            .format(DateTimeFormatter
                .ofPattern("yyyyMMdd"));
        String random = UUID.randomUUID()
            .toString()
            .substring(0, 6)
            .toUpperCase();
        return "INV-" + date + "-" + random;
    }

    // TXN-20260414-XXXX
    public String generateTransactionId() {
        String date = LocalDateTime.now()
            .format(DateTimeFormatter
                .ofPattern("yyyyMMdd"));
        String random = UUID.randomUUID()
            .toString()
            .substring(0, 8)
            .toUpperCase();
        return "TXN-" + date + "-" + random;
    }
}