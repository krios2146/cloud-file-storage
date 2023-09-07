package com.file.storage.dto.request.folder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FolderRenameRequest extends FolderRequest {

    private String currentName;

    private String newName;

    private String path;

    public FolderRenameRequest(String owner, String currentName, String newName, String path) {
        super(owner);
        this.currentName = currentName;
        this.newName = newName;
        this.path = path;
    }
}
