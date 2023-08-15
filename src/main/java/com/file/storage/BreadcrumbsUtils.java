package com.file.storage;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class BreadcrumbsUtils {

    public static List<String> getBreadcrumbLinksForPath(String path) {
        if (path.isEmpty()) {
            return List.of(path);
        }

        List<String> links = new ArrayList<>();

        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == '/') {
                links.add(path.substring(0, i));
            }
        }

        return links;
    }
        return Arrays.stream(path.split("/")).toList();
    }
}
