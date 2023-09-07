package com.file.storage.dto.request.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileRenameRequest extends FileRequest {

    private String currentName;

    private String newName;

    private String path;
     
    public FileRenameRequest(String owner, String currentName, String newName, String path) {
        super(owner);
        this.currentName = currentName;
        this.newName = newName;
        this.path = path;
    }
}
