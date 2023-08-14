package com.file.storage.service;

import com.file.storage.MinioHelper;
import com.file.storage.config.MinioBucketConfiguration;
import com.file.storage.dto.FolderUploadRequest;
import io.minio.MinioClient;
import io.minio.SnowballObject;
import io.minio.UploadSnowballObjectsArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final MinioClient minioClient;
    private final MinioBucketConfiguration minioBucketConfiguration;
    private final MinioHelper minioHelper;

    public void uploadFolder(FolderUploadRequest folderUploadRequest) {
        List<MultipartFile> files = folderUploadRequest.getFiles();
        String owner = folderUploadRequest.getOwner();
        try {
            List<SnowballObject> objects = convertToObjects(files, owner);

            minioClient.uploadSnowballObjects(UploadSnowballObjectsArgs.builder()
                    .bucket(minioBucketConfiguration.getBucketName())
                    .objects(objects)
                    .build());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<SnowballObject> convertToObjects(List<MultipartFile> files, String owner) throws IOException {
        List<SnowballObject> objects = new ArrayList<>();

        for (MultipartFile file : files) {
            SnowballObject snowballObject = new SnowballObject(
                    minioHelper.getRootFolderForUser(owner) + file.getOriginalFilename(),
                    file.getInputStream(),
                    file.getSize(),
                    null
            );
            objects.add(snowballObject);
        }

        return objects;
    }
}
