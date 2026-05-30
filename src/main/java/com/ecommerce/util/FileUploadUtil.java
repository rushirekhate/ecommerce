package com.ecommerce.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class FileUploadUtil {

    // Upload directory
    private static final String UPLOAD_DIR = 
        "uploads/";

    // Allowed image types
    private static final List<String> ALLOWED_TYPES = 
        Arrays.asList(
            "image/jpeg", 
            "image/png", 
            "image/webp",
            "image/gif");

    // Max file size — 5MB
    private static final long MAX_SIZE = 
        5 * 1024 * 1024;

    public String uploadFile(MultipartFile file,
            String folder) throws IOException {

        // Validation
        validateFile(file);

        // Folder create karo
        String uploadPath = UPLOAD_DIR + folder + "/";
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Unique filename
        String originalName = 
            file.getOriginalFilename();
        String extension = originalName
            .substring(originalName.lastIndexOf("."));
        String fileName = UUID.randomUUID()
            .toString() + extension;

        // Save file
        Path path = Paths.get(uploadPath + fileName);
        Files.write(path, file.getBytes());

        return uploadPath + fileName;
    }

    // Product image upload
    public String uploadProductImage(
            MultipartFile file) throws IOException {
        return uploadFile(file, "products");
    }

    // Profile image upload
    public String uploadProfileImage(
            MultipartFile file) throws IOException {
        return uploadFile(file, "profiles");
    }

    // Banner image upload
    public String uploadBannerImage(
            MultipartFile file) throws IOException {
        return uploadFile(file, "banners");
    }

    // Blog thumbnail upload
    public String uploadBlogThumbnail(
            MultipartFile file) throws IOException {
        return uploadFile(file, "blogs");
    }

    // Delete file
    public void deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(
                "File delete failed: " 
                    + e.getMessage());
        }
    }

    // Validate file
    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException(
                "File is empty!");
        }
        if (file.getSize() > MAX_SIZE) {
            throw new RuntimeException(
                "File size exceeds 5MB!");
        }
        if (!ALLOWED_TYPES.contains(
                file.getContentType())) {
            throw new RuntimeException(
                "Invalid file type! "
                + "Only JPG, PNG, WEBP allowed!");
        }
    }
}
