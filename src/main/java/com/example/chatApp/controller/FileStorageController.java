package com.example.chatApp.controller;


import com.example.chatApp.dto.FileUploadResponse;
import com.example.chatApp.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;


@Slf4j
@RestController
@RequestMapping("/api" + FileStorageController.UPLOAD_URI)
public class FileStorageController {
    static final String UPLOAD_URI = "/upload/";
//    private final FileStorageService fileStorageService;
//    @Value("${env.host.url}")
//    public String HOST_URL;
//
//    public FileStorageController(FileStorageService fileStorageService, @Value("${env.host.url}") String HOST_URL) {
//        this.fileStorageService = fileStorageService;
//        this.HOST_URL = HOST_URL;
//    }
//
//    @PostMapping(path = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public FileUploadResponse uploadFile(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException, URISyntaxException {
//        log.info("REST request to POST file upload: {}", file);
//        String fileName = fileStorageService.storeFile(file);
//        String fileDownloadUri = HOST_URL + "/api" + UPLOAD_URI + fileName;
//        return new FileUploadResponse(fileName, file.getContentType(), fileDownloadUri);
//    }
//
//    @GetMapping(value = "/{fileName}")
//    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
//        log.info("REST request to GET file: {}", fileName);
//        Resource resource = fileStorageService.loadFileAsResource(fileName);
//        String contentType = null;
//
//        contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//
//        if (contentType == null) {
//            contentType = "application/octet-stream";
//        }
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(contentType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
//    }

}
