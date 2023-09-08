package com.file.storage.dto.request.file;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileUploadRequest extends FileRequest {

    @NotBlank(message = "You must specify the file to upload")
    private MultipartFile file;

    public FileUploadRequest(String owner, MultipartFile file) {
        super(owner);
        this.file = file;
    }
}
