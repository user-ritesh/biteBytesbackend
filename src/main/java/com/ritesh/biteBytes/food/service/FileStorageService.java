package com.ritesh.biteBytes.food.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    private final String UPLOAD_DIR = "uploads/";

    public String saveFile(MultipartFile file) throws IOException {
        // Ensure directory exists
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Generate filename similar to Date.now() + originalname
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);

        // Save the file
        Files.write(filePath, file.getBytes());
        return fileName;
    }

    public void deleteFile(String fileName) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.out.println("Could not delete file: " + e.getMessage());
        }
    }
}
