package com.file.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FileRenameRequest {

    private String currentName;

    private String newName;

    private String path;

    private String owner;
}
