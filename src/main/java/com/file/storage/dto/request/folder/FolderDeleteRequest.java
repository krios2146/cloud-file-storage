package com.file.storage.dto.request.folder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FolderDeleteRequest {

    private String path;

    private String owner;
}
