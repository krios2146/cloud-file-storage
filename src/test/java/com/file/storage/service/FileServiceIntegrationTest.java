package com.file.storage.service;

import com.file.storage.dto.MinioObjectDto;
import com.file.storage.dto.file.FileUploadRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("dev")
class FileServiceIntegrationTest {

    private static final DockerImageName MINIO_IMAGE = DockerImageName.parse("quay.io/minio/minio");

    @Container
    private static final GenericContainer<?> minio = new GenericContainer<>(MINIO_IMAGE)
            .withCommand("server /data")
            .withEnv("MINIO_ROOT_USER", "user")
            .withEnv("MINIO_ROOT_PASSWORD", "minio_password")
            .withExposedPorts(9000);

    @Autowired
    private FileService fileService;

    @Test
    void uploadFile_saveFileToMinio() {
        MockMultipartFile file = new MockMultipartFile(
                "name",
                "File name",
                "text/plain",
                "Content".getBytes(StandardCharsets.UTF_8)
        );
        FileUploadRequest fileUploadRequest = new FileUploadRequest();
        fileUploadRequest.setFile(file);
        fileUploadRequest.setOwner("user");

        fileService.uploadFile(fileUploadRequest);

        List<MinioObjectDto> files = fileService.getUserFiles("user", "");
        assertEquals(1, files.size());
        MinioObjectDto minioFile = files.get(0);
        assertEquals(file.getOriginalFilename(), minioFile.getName());
    }

    @DynamicPropertySource
    static void minioProperties(DynamicPropertyRegistry registry) {
        registry.add("minio.client.endpoint", () -> "http://127.0.0.1:" + minio.getMappedPort(9000));
        registry.add("minio.client.user", () -> "user");
        registry.add("minio.client.password", () -> "minio_password");
        registry.add("minio.bucket-name", () -> "user-files");
    }
}