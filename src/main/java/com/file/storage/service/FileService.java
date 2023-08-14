package com.file.storage.service;

import com.file.storage.MinioHelper;
import com.file.storage.config.MinioBucketConfiguration;
import com.file.storage.dto.FileUploadRequest;
import com.file.storage.dto.MinioObjectDto;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.SnowballObject;
import io.minio.UploadSnowballObjectsArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final MinioClient minioClient;
    private final MinioBucketConfiguration minioBucketConfiguration;
    private final MinioHelper minioHelper;

    public void uploadFile(FileUploadRequest fileUploadRequest) {
        MultipartFile file = fileUploadRequest.getFile();
        try (InputStream stream = file.getInputStream()) {
            minioClient.putObject(PutObjectArgs.builder()
                    .stream(stream, file.getSize(), -1)
                    .bucket(minioBucketConfiguration.getBucketName())
                    .object(minioHelper.getRootFolderForUser(fileUploadRequest.getOwner()) + file.getOriginalFilename())
                    .build());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void uploadFiles(List<MultipartFile> files) {
        try {
            List<SnowballObject> objects = convertToObjects(files);

            minioClient.uploadSnowballObjects(UploadSnowballObjectsArgs.builder()
                    .bucket(minioBucketConfiguration.getBucketName())
                    .objects(objects)
                    .build());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<SnowballObject> convertToObjects(List<MultipartFile> files) throws IOException {
        List<SnowballObject> objects = new ArrayList<>();

        for (MultipartFile file : files) {
            SnowballObject snowballObject = new SnowballObject(
                    minioHelper.getRootFolder() + file.getOriginalFilename(),
                    file.getInputStream(),
                    file.getSize(),
                    null
            );
            objects.add(snowballObject);
        }

        return objects;
    }
}
