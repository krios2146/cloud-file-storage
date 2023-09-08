package com.file.storage.dto.request.folder;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FolderUploadRequest extends FolderRequest {

    @Size(min = 1, message = "Empty folder upload is not supported")
    private List<MultipartFile> files;

    public FolderUploadRequest(String owner, List<MultipartFile> files) {
        super(owner);
        this.files = files;
    }
}
