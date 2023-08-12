package com.file.storage.service;

import com.file.storage.MinioHelper;
import com.file.storage.config.MinioBucketConfiguration;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class FileService {

    private final MinioClient minioClient;
    private final MinioBucketConfiguration minioBucketConfiguration;
    private final MinioHelper minioHelper;

    public void uploadFile(MultipartFile file) {
        try (InputStream stream = file.getInputStream()) {
            minioClient.putObject(PutObjectArgs.builder()
                    .stream(stream, file.getSize(), -1)
                    .bucket(minioBucketConfiguration.getBucketName())
                    .object(minioHelper.getRootFolder() + file.getOriginalFilename())
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
