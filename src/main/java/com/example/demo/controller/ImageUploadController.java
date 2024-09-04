package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"})
public class ImageUploadController {

    @Value("${file.upload-dir}")
    private String uploadDir;
    @RequestMapping(value="/uploads",method ={RequestMethod.POST})
    public ResponseEntity<Map<String, String>> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            // Ensure upload directory exists
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Save the file to the upload directory
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.copy(file.getInputStream(), filePath);
            System.out.println(filePath);

            // Construct the file URL
            String fileUrl = "/uploads/" + fileName;

            // Return the file URL in the response
            Map<String, String> response = new HashMap<>();
            response.put("url", fileUrl);
System.out.println("uploaded");
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
