package com.file.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MinioHelper {

    public String getRootFolderForUser(String username) {
        return "user-" + username + "-files/";
    }
}
