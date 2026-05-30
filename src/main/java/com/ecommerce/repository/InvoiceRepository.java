package com.ecommerce.repository;

import com.ecommerce.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository 
    extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findByInvoiceNumber(
        String invoiceNumber);
    
    Optional<Invoice> findByOrderId(Long orderId);
    
    List<Invoice> findByUserId(Long userId);
}
