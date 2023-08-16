package com.file.storage.service;

import com.file.storage.config.MinioBucketConfiguration;
import com.file.storage.dto.FileDeleteRequest;
import com.file.storage.dto.FileUploadRequest;
import com.file.storage.dto.MinioObjectDto;
import io.minio.*;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.file.storage.MinioRootFolderUtils.getUserRootFolderPrefix;
import static com.file.storage.MinioRootFolderUtils.removeUserRootFolderPrefix;

@Service
@RequiredArgsConstructor
public class FileService {

    private final MinioClient minioClient;
    private final MinioBucketConfiguration minioBucketConfiguration;

    public void uploadFile(FileUploadRequest fileUploadRequest) {
        MultipartFile file = fileUploadRequest.getFile();
        try (InputStream stream = file.getInputStream()) {
            minioClient.putObject(PutObjectArgs.builder()
                    .stream(stream, file.getSize(), -1)
                    .bucket(minioBucketConfiguration.getBucketName())
                    .object(getUserRootFolderPrefix(fileUploadRequest.getOwner()) + file.getOriginalFilename())
                    .build());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<MinioObjectDto> getUserFiles(String username, String folder) {
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder()
                .bucket(minioBucketConfiguration.getBucketName())
                .prefix(getUserRootFolderPrefix(username) + folder)
                .build());

        List<MinioObjectDto> files = new ArrayList<>();

        results.forEach(result -> {
            try {
                Item item = result.get();
                MinioObjectDto object = new MinioObjectDto(
                        username,
                        item.isDir(),
                        removeUserRootFolderPrefix(item.objectName(), username),
                        getFileNameFromPath(item.objectName())
                );
                files.add(object);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return files;
    }

    public void deleteFile(FileDeleteRequest fileDeleteRequest) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(minioBucketConfiguration.getBucketName())
                    .object(getUserRootFolderPrefix(fileDeleteRequest.getOwner()) + fileDeleteRequest.getName())
                    .build());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFileNameFromPath(String path) {
        if (!path.contains("/")) {
            return path;
        }
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        if (path.lastIndexOf('/') > 0) {
            return path.substring(path.lastIndexOf('/') + 1);
        }
        return path;
    }
}
