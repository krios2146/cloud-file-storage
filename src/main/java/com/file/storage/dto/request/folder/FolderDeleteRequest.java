package com.file.storage.dto.request.folder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FolderDeleteRequest extends FolderRequest {

    private String path;

    public FolderDeleteRequest(String owner, String path) {
        super(owner);
        this.path = path;
    }
}
