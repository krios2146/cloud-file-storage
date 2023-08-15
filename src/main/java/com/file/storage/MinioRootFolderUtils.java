package com.file.storage;

public class MinioRootFolderUtils {

    public static String getRootFolderForUser(String username) {
        return "user-" + username + "-files/";
    }
}
