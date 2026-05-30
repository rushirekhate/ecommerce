package com.ecommerce.service.impl;

import com.ecommerce.entity.Invoice;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.User;
import com.ecommerce.repository.InvoiceRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.service.interfaces.IInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl 
    implements IInvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final OrderRepository orderRepository;

    @Override
    public Invoice generateInvoice(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException(
                "Order not found!"));

        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setUser(order.getUser());
        invoice.setInvoiceNumber(
            generateInvoiceNumber());
        invoice.setSubtotal(order.getTotalAmount());
        invoice.setDiscount(order.getDiscountAmount());
        invoice.setTax(0.0);
        invoice.setTotalAmount(order.getFinalAmount());
        invoice.setStatus(
            Invoice.InvoiceStatus.GENERATED);

        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(
                "Invoice not found!"));
    }

    @Override
    public Invoice getInvoiceByOrderId(Long orderId) {
        return invoiceRepository.findByOrderId(orderId)
            .orElseThrow(() -> new RuntimeException(
                "Invoice not found!"));
    }

    @Override
    public Invoice getInvoiceByNumber(
            String invoiceNumber) {
        return invoiceRepository
            .findByInvoiceNumber(invoiceNumber)
            .orElseThrow(() -> new RuntimeException(
                "Invoice not found!"));
    }

    @Override
    public List<Invoice> getInvoicesByUserId(
            Long userId) {
        return invoiceRepository.findByUserId(userId);
    }

    @Override
    public byte[] generateInvoicePdf(Long invoiceId) {
        // PDF util se implement karenge
        return new byte[0];
    }

    @Override
    public void sendInvoiceByEmail(Long invoiceId) {
        // Email util se implement karenge
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    private String generateInvoiceNumber() {
        return "INV-" + System.currentTimeMillis() +
            "-" + UUID.randomUUID()
                .toString().substring(0, 4)
                .toUpperCase();
    }
}