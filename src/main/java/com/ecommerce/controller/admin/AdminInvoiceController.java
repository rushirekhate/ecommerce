package com.ecommerce.controller.admin;


import com.ecommerce.dto.ApiResponseDto;
import com.ecommerce.entity.Invoice;
import com.ecommerce.service.interfaces.IInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/invoices")
@RequiredArgsConstructor
public class AdminInvoiceController {

    private final IInvoiceService invoiceService;

    @PostMapping("/generate/{orderId}")
    public ResponseEntity<ApiResponseDto<Invoice>>
            generate(@PathVariable Long orderId) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Invoice generated!",
                invoiceService
                    .generateInvoice(orderId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<Invoice>>>
            getAllInvoices() {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                invoiceService.getAllInvoices()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Invoice>>
            getById(@PathVariable Long id) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                invoiceService.getInvoiceById(id)));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponseDto<Invoice>>
            getByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(
            ApiResponseDto.success("Fetched!",
                invoiceService
                    .getInvoiceByOrderId(orderId)));
    }

    @GetMapping("/pdf/{invoiceId}")
    public ResponseEntity<byte[]> downloadPdf(
            @PathVariable Long invoiceId) {
        byte[] pdf = invoiceService
            .generateInvoicePdf(invoiceId);
        return ResponseEntity.ok()
            .header("Content-Type",
                "application/pdf")
            .header("Content-Disposition",
                "attachment; filename=invoice.pdf")
            .body(pdf);
    }
}