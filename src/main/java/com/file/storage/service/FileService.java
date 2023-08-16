package com.file.storage.service;

import com.file.storage.config.MinioBucketConfiguration;
import com.file.storage.dto.FileDeleteRequest;
import com.file.storage.dto.FileRenameRequest;
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

    public List<MinioObjectDto> getUserFiles(String username, String folder) {
        return getUserFiles(username, folder, false);
    }

    public List<MinioObjectDto> getAllUserFiles(String username, String folder) {
        return getUserFiles(username, folder, true);
    }

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

    public void renameFile(FileRenameRequest fileRenameRequest) {
        try {
            minioClient.copyObject(CopyObjectArgs.builder()
                    .bucket(minioBucketConfiguration.getBucketName())
                    .object(getPathWithNewName(
                            (getUserRootFolderPrefix(fileRenameRequest.getOwner()) + fileRenameRequest.getPath()),
                            fileRenameRequest.getCurrentName(),
                            fileRenameRequest.getNewName())
                    )
                    .source(CopySource.builder()
                            .bucket(minioBucketConfiguration.getBucketName())
                            .object(getUserRootFolderPrefix(fileRenameRequest.getOwner()) + fileRenameRequest.getPath())
                            .build())
                    .build());

            deleteFile(new FileDeleteRequest(
                    fileRenameRequest.getPath(),
                    fileRenameRequest.getOwner())
            );
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFile(FileDeleteRequest fileDeleteRequest) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(minioBucketConfiguration.getBucketName())
                    .object(getUserRootFolderPrefix(fileDeleteRequest.getOwner()) + fileDeleteRequest.getPath())
                    .build());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<MinioObjectDto> getUserFiles(String username, String folder, boolean isRecursive) {
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder()
                .bucket(minioBucketConfiguration.getBucketName())
                .prefix(getUserRootFolderPrefix(username) + folder)
                .recursive(isRecursive)
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

    private static String getPathWithNewName(String path, String oldName, String newName) {
        return path.replace(oldName, newName);
    }
}
