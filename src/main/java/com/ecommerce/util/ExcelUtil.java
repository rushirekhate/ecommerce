package com.ecommerce.util;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ExcelUtil {

    // Orders Excel
    public byte[] generateOrdersExcel(
            List<Order> orders) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook
            .createSheet("Orders");

        // Header style
        CellStyle headerStyle = 
            workbook.createCellStyle();
        headerStyle.setFillForegroundColor(
            IndexedColors.BLUE.getIndex());
        headerStyle.setFillPattern(
            FillPatternType.SOLID_FOREGROUND);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(
            IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);

        // Headers
        Row headerRow = sheet.createRow(0);
        String[] headers = {
            "Order Number", "Customer",
            "Email", "Total Amount",
            "Status", "Payment Method",
            "Date"
        };

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
            sheet.autoSizeColumn(i);
        }

        // Data rows
        int rowNum = 1;
        for (Order order : orders) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(
                order.getOrderNumber());
            row.createCell(1).setCellValue(
                order.getUser().getName());
            row.createCell(2).setCellValue(
                order.getUser().getEmail());
            row.createCell(3).setCellValue(
                order.getFinalAmount());
            row.createCell(4).setCellValue(
                order.getStatus().toString());
            row.createCell(5).setCellValue(
                order.getPaymentMethod().toString());
            row.createCell(6).setCellValue(
                order.getCreatedAt().toString());
        }

        // Auto size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = 
            new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return out.toByteArray();
    }

    // Products Excel
    public byte[] generateProductsExcel(
            List<Product> products) 
            throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook
            .createSheet("Products");

        // Header style
        CellStyle headerStyle = 
            workbook.createCellStyle();
        headerStyle.setFillForegroundColor(
            IndexedColors.GREEN.getIndex());
        headerStyle.setFillPattern(
            FillPatternType.SOLID_FOREGROUND);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(
            IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);

        // Headers
        Row headerRow = sheet.createRow(0);
        String[] headers = {
            "ID", "Name", "Brand",
            "Price", "Discount Price",
            "Stock", "Rating", "Category",
            "Status"
        };

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Data rows
        int rowNum = 1;
        for (Product product : products) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(
                product.getId());
            row.createCell(1).setCellValue(
                product.getName());
            row.createCell(2).setCellValue(
                product.getBrand());
            row.createCell(3).setCellValue(
                product.getPrice());
            row.createCell(4).setCellValue(
                product.getDiscountPrice() != null
                    ? product.getDiscountPrice() : 0);
            row.createCell(5).setCellValue(
                product.getStock());
            row.createCell(6).setCellValue(
                product.getRating());
            row.createCell(7).setCellValue(
                product.getCategory() != null
                    ? product.getCategory().getName()
                    : "N/A");
            row.createCell(8).setCellValue(
                product.isActive() 
                    ? "Active" : "Inactive");
        }

        // Auto size
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = 
            new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return out.toByteArray();
    }

    // Users Excel
    public byte[] generateUsersExcel(
            List<User> users) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users");

        // Header style
        CellStyle headerStyle = 
            workbook.createCellStyle();
        headerStyle.setFillForegroundColor(
            IndexedColors.ORANGE.getIndex());
        headerStyle.setFillPattern(
            FillPatternType.SOLID_FOREGROUND);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(
            IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);

        // Headers
        Row headerRow = sheet.createRow(0);
        String[] headers = {
            "ID", "Name", "Email",
            "Phone", "Role",
            "Verified", "Active",
            "Created At"
        };

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Data rows
        int rowNum = 1;
        for (User user : users) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(
                user.getId());
            row.createCell(1).setCellValue(
                user.getName());
            row.createCell(2).setCellValue(
                user.getEmail());
            row.createCell(3).setCellValue(
                user.getPhone());
            row.createCell(4).setCellValue(
                user.getRole().toString());
            row.createCell(5).setCellValue(
                user.isEmailVerified() 
                    ? "Yes" : "No");
            row.createCell(6).setCellValue(
                user.isActive() ? "Yes" : "No");
            row.createCell(7).setCellValue(
                user.getCreatedAt().toString());
        }

        // Auto size
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = 
            new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return out.toByteArray();
    }
}