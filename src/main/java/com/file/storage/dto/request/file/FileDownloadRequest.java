package com.file.storage.dto.request.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileDownloadRequest extends FileRequest {

    private String path;

    private String name;

    public FileDownloadRequest(String owner, String path, String name) {
        super(owner);
        this.path = path;
        this.name = name;
    }
}
