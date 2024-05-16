package com.example.chatApp.service;

import com.example.chatApp.exception.FileStorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
public class FileStorageService {
//
//    private final Path fileStorageLocation;
//
//
//    public FileStorageService(@Value("${file.storage.location}") String fileStorageLocation) throws IOException {
//        this.fileStorageLocation = Paths.get((fileStorageLocation))
//                .toAbsolutePath().normalize();
//
//        Files.createDirectories(this.fileStorageLocation);
//
//    }
//
//    public String storeFile(MultipartFile file) throws IOException {
//
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        String generatedFileName = generateFileName(fileName);
//
//        if (fileName.contains("..")) {
//            throw new FileStorageException("Excuse me! The file name contains an invalid sequence: " + generatedFileName);
//        } else {
//            Path targetLocation = this.fileStorageLocation.resolve(generatedFileName);
//            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//
//            return generatedFileName;
//        }
//    }
//
//
//    public Resource loadFileAsResource(String fileName) {
//        try {
//            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//            if (resource.exists()) {
//                return resource;
//            } else {
//                throw new FileStorageException("File not found " + fileName);
//            }
//        } catch (MalformedURLException ex) {
//            throw new FileStorageException("File not found " + fileName);
//        }
//    }
//
//    public String generateFileName(String fileName) {
//        String name = fileName.substring(0, fileName.length() - 4);
//        UUID uuid = UUID.randomUUID();
//        name = name + uuid + fileName.substring(fileName.length() - 4);
//        return name;
//    }

}
