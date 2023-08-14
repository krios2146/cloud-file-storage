package com.file.storage;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class BreadcrumbsUtils {

    public static List<String> getBreadcrumbsForPath(String path) {
        return Arrays.stream(path.split("/")).toList();
    }
}
