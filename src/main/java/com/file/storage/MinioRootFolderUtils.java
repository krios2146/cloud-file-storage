package com.file.storage;

public class MinioRootFolderUtils {

    public static String getRootFolderForUser(String username) {
        return "user-" + username + "-files/";
    }

    public static String removeUserRootFolderPrefix(String path, String username) {
        String rootFolder = getRootFolderForUser(username);

        return path.replaceAll(rootFolder, "");
    }
}
