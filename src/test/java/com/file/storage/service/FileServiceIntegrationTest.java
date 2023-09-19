package com.file.storage.service;

import com.file.storage.dto.MinioObjectDto;
import com.file.storage.dto.file.FileDeleteRequest;
import com.file.storage.dto.file.FileRenameRequest;
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
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
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

        fileService.uploadFile(new FileUploadRequest("user", file));

        List<MinioObjectDto> files = fileService.getUserFiles("user", "");
        assertEquals(1, files.size());
        MinioObjectDto minioFile = files.get(0);
        assertEquals(file.getOriginalFilename(), minioFile.getName());
    }

    @Test
    void deleteFile_deleteFileFromMinio() {
        MockMultipartFile file = new MockMultipartFile(
                "name",
                "File to delete",
                "text/plain",
                "Content".getBytes(StandardCharsets.UTF_8)
        );
        fileService.uploadFile(new FileUploadRequest("user", file));

        fileService.deleteFile(new FileDeleteRequest("user", "/File to delete"));

        List<MinioObjectDto> files = fileService.getUserFiles("user", "");
        long actual = files.stream()
                .filter(f -> f.getName().equals("File to delete"))
                .count();
        assertEquals(0, actual);
    }

    @Test
    void renameFile_renameFileInMinio() {
        MockMultipartFile file = new MockMultipartFile(
                "name",
                "File to rename",
                "text/plain",
                "Content".getBytes(StandardCharsets.UTF_8)
        );
        fileService.uploadFile(new FileUploadRequest("user", file));

        fileService.renameFile(new FileRenameRequest(
                "user",
                "File to rename",
                "Renamed file",
                "File to rename")
        );

        List<MinioObjectDto> files = fileService.getUserFiles("user", "");
        long filesWithOldName = files.stream()
                .filter(f -> f.getName().equals("File to rename"))
                .count();
        long filesWithNewName = files.stream()
                .filter(f -> f.getName().equals("Renamed file"))
                .count();
        assertEquals(0, filesWithOldName);
        assertEquals(1, filesWithNewName);
    }

    @Test
    void searchUserFile_showOnlyUserFile() {
        MockMultipartFile file = new MockMultipartFile(
                "name",
                "File to search",
                "text/plain",
                "Content".getBytes(StandardCharsets.UTF_8)
        );
        fileService.uploadFile(new FileUploadRequest("user", file));
        fileService.uploadFile(new FileUploadRequest("admin", file));
        fileService.uploadFile(new FileUploadRequest("root", file));

        List<MinioObjectDto> files = fileService.getAllUserFiles("user", "");
        assertEquals(1, files.size());
    }

    @DynamicPropertySource
    static void minioProperties(DynamicPropertyRegistry registry) {
        registry.add("minio.client.endpoint", () -> "http://127.0.0.1:" + minio.getMappedPort(9000));
        registry.add("minio.client.user", () -> "user");
        registry.add("minio.client.password", () -> "minio_password");
        registry.add("minio.bucket-name", () -> "user-files");
    }
}