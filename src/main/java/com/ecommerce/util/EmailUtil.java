package com.ecommerce.util;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class EmailUtil {

    private final JavaMailSender mailSender;

    // Simple email
    public void sendEmail(String to,
            String subject, String body) {
        try {
            MimeMessage message = 
                mailSender.createMimeMessage();
            MimeMessageHelper helper = 
                new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(
                "Email send failed: " + e.getMessage());
        }
    }

    // OTP email
    public void sendOtpEmail(
            String to, String otp) {
        String subject = "Your OTP Code";
        String body = "<h2>Your OTP is: " 
            + "<b>" + otp + "</b></h2>"
            + "<p>Valid for 5 minutes only.</p>"
            + "<p>Do not share with anyone!</p>";
        sendEmail(to, subject, body);
    }

    // Order confirmation email
    public void sendOrderConfirmationEmail(
            String to, String orderNumber,
            Double amount) {
        String subject = 
            "Order Confirmed - " + orderNumber;
        String body = "<h2>Order Confirmed!</h2>"
            + "<p>Order Number: <b>" 
            + orderNumber + "</b></p>"
            + "<p>Total Amount: <b>₹" 
            + amount + "</b></p>"
            + "<p>Thank you for shopping!</p>";
        sendEmail(to, subject, body);
    }

    // Order status update email
    public void sendOrderStatusEmail(
            String to, String orderNumber,
            String status) {
        String subject = 
            "Order Update - " + orderNumber;
        String body = "<h2>Order Status Updated!</h2>"
            + "<p>Order Number: <b>" 
            + orderNumber + "</b></p>"
            + "<p>Status: <b>" + status + "</b></p>";
        sendEmail(to, subject, body);
    }

    // Password reset email
    public void sendPasswordResetEmail(
            String to, String otp) {
        String subject = "Password Reset Request";
        String body = "<h2>Password Reset OTP</h2>"
            + "<p>Your OTP is: <b>" + otp + "</b></p>"
            + "<p>Valid for 5 minutes only.</p>"
            + "<p>If you didn't request this, "
            + "ignore this email.</p>";
        sendEmail(to, subject, body);
    }

    // Invoice email with attachment
    public void sendInvoiceEmail(
            String to, String orderNumber,
            byte[] pdfAttachment) {
        try {
            MimeMessage message = 
                mailSender.createMimeMessage();
            MimeMessageHelper helper = 
                new MimeMessageHelper(
                    message, true);
            helper.setTo(to);
            helper.setSubject(
                "Invoice - " + orderNumber);
            helper.setText(
                "<h2>Please find your invoice "
                + "attached.</h2>", true);
            helper.addAttachment(
                "Invoice-" + orderNumber + ".pdf",
                new org.springframework.core
                    .io.ByteArrayResource(
                        pdfAttachment));
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(
                "Email send failed: " + e.getMessage());
        }
    }
}