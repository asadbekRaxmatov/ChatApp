package com.example.chatApp.service;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

@Service
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    private static final String BUCKET_NAME = "images";

    public String uploadFile(MultipartFile file) {
        try {
            // Check if the bucket exists, and create if it doesn't
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build());
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(BUCKET_NAME).build());
            }

            // Generate a unique object name
            String objectName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            // Upload the file
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(BUCKET_NAME).object(objectName).stream(
                                    file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());

            // Generate presigned URL for the uploaded object
            String presignedUrl = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(BUCKET_NAME)
                            .object(objectName)
                            .expiry(7, TimeUnit.DAYS) // Link valid for 7 days
                            .build());

            return presignedUrl;

        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while uploading file to MinIO", e);
        }
    }
}

