package com.file.storage.dto.request.file;

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

    private MultipartFile file;

    public FileUploadRequest(String owner, MultipartFile file) {
        super(owner);
        this.file = file;
    }
}
