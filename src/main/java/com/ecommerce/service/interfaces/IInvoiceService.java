package com.ecommerce.service.interfaces;

import com.ecommerce.entity.Invoice;
import java.util.List;

public interface IInvoiceService {

    // Generate
    Invoice generateInvoice(Long orderId);
    
    // Get
    Invoice getInvoiceById(Long id);
    Invoice getInvoiceByOrderId(Long orderId);
    Invoice getInvoiceByNumber(String invoiceNumber);
    List<Invoice> getInvoicesByUserId(Long userId);

    // PDF
    byte[] generateInvoicePdf(Long invoiceId);
    void sendInvoiceByEmail(Long invoiceId);

    // Admin
    List<Invoice> getAllInvoices();
}
