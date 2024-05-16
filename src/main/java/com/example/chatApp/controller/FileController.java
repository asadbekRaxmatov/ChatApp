package com.example.chatApp.controller;

import com.example.chatApp.service.MinioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/files")
public class FileController {
//
//    @Autowired
//    private MinioService minioService;
//
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
//        log.info("REST request to POST file upload: {}", file);
//
//        String url = minioService.uploadFile(file);
//        return ResponseEntity.ok(url);
//    }

}
