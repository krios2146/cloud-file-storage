package com.file.storage.service;

import com.file.storage.MinioHelper;
import com.file.storage.config.MinioBucketConfiguration;
import com.file.storage.dto.FileUploadRequest;
import com.file.storage.dto.MinioObjectDto;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public List<MinioObjectDto> getUserFiles(String username, String folder) {
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder()
                .bucket(minioBucketConfiguration.getBucketName())
                .prefix(minioHelper.getRootFolderForUser(username) + folder)
                .build());

        List<MinioObjectDto> files = new ArrayList<>();

        results.forEach(result -> {
            try {
                Item item = result.get();
                MinioObjectDto object = new MinioObjectDto(
                        username,
                        item.isDir(),
                        item.objectName()
                );
                files.add(object);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return files;
    }
}
