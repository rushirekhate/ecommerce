package com.ecommerce.util;

import com.ecommerce.entity.Invoice;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class PdfUtil {

    private static final Font TITLE_FONT =
        new Font(Font.FontFamily.HELVETICA,
            18, Font.BOLD);

    private static final Font HEADER_FONT =
        new Font(Font.FontFamily.HELVETICA,
            12, Font.BOLD);

    private static final Font NORMAL_FONT =
        new Font(Font.FontFamily.HELVETICA,
            10, Font.NORMAL);

    public byte[] generateInvoicePdf(
            Invoice invoice) {
        try {
            ByteArrayOutputStream out = 
                new ByteArrayOutputStream();
            Document document = new Document(
                PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            // Title
            Paragraph title = new Paragraph(
                "INVOICE", TITLE_FONT);
            title.setAlignment(
                Element.ALIGN_CENTER);
            document.add(title);
            document.add(
                new Paragraph(" "));

            // Invoice details
            Order order = invoice.getOrder();
            document.add(new Paragraph(
                "Invoice Number: " 
                + invoice.getInvoiceNumber(),
                HEADER_FONT));
            document.add(new Paragraph(
                "Order Number: " 
                + order.getOrderNumber(),
                NORMAL_FONT));
            document.add(new Paragraph(
                "Date: " + invoice.getCreatedAt()
                    .format(DateTimeFormatter
                        .ofPattern("dd-MM-yyyy")),
                NORMAL_FONT));
            document.add(new Paragraph(" "));

            // Customer details
            document.add(new Paragraph(
                "Customer Details:", HEADER_FONT));
            document.add(new Paragraph(
                "Name: " + order.getUser().getName(),
                NORMAL_FONT));
            document.add(new Paragraph(
                "Email: " + order.getUser().getEmail(),
                NORMAL_FONT));
            document.add(new Paragraph(
                "Address: " + order.getAddressLine()
                + ", " + order.getCity()
                + ", " + order.getState()
                + " - " + order.getPincode(),
                NORMAL_FONT));
            document.add(new Paragraph(" "));

            // Order items table
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{
                3f, 1f, 1.5f, 1.5f});

            // Table headers
            addTableHeader(table, "Product");
            addTableHeader(table, "Qty");
            addTableHeader(table, "Price");
            addTableHeader(table, "Total");

            // Table rows
            List<OrderItem> items = 
                order.getOrderItems();
            if (items != null) {
                for (OrderItem item : items) {
                    table.addCell(new Phrase(
                        item.getProduct().getName(),
                        NORMAL_FONT));
                    table.addCell(new Phrase(
                        String.valueOf(
                            item.getQuantity()),
                        NORMAL_FONT));
                    table.addCell(new Phrase(
                        "₹" + item.getPrice(),
                        NORMAL_FONT));
                    table.addCell(new Phrase(
                        "₹" + item.getTotalPrice(),
                        NORMAL_FONT));
                }
            }
            document.add(table);
            document.add(new Paragraph(" "));

            // Amount summary
            document.add(new Paragraph(
                "Subtotal: ₹" + invoice.getSubtotal(),
                NORMAL_FONT));
            document.add(new Paragraph(
                "Discount: ₹" + invoice.getDiscount(),
                NORMAL_FONT));
            document.add(new Paragraph(
                "Total: ₹" + invoice.getTotalAmount(),
                HEADER_FONT));

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException(
                "PDF generation failed: "
                    + e.getMessage());
        }
    }

    private void addTableHeader(
            PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(
            new Phrase(text, HEADER_FONT));
        cell.setBackgroundColor(
            BaseColor.LIGHT_GRAY);
        cell.setPadding(5);
        table.addCell(cell);
    }
}