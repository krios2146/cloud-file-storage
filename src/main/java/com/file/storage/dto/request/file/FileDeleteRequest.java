package com.file.storage.dto.request.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileDeleteRequest extends FileRequest {

    private String path;

    public FileDeleteRequest(String owner, String path) {
        super(owner);
        this.path = path;
    }
}
