package com.file.storage.service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class BucketService {
    private final MinioClient minioClient = MinioClient.builder()
            .credentials("minioadmin", "minioadmin")
            .endpoint("http://127.0.0.1:9000")
            .build();

    public boolean isBucketExists(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(bucketName)
                .build());
    }

    public void createBucket(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.makeBucket(MakeBucketArgs.builder()
                .bucket(bucketName)
                .build());
    }
}
