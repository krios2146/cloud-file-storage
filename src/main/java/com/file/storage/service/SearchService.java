package com.file.storage.service;

import com.file.storage.dto.MinioObjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final FileService fileService;

    public List<MinioObjectDto> search(String username, String query) {
        List<MinioObjectDto> files = fileService.getAllUserFiles(username, "");

        // delete files from paths
        for (MinioObjectDto file : files) {
            String path = file.getPath();
            path = path.replace(file.getName(), "");
            file.setPath(path);
        }

        List<MinioObjectDto> foundFiles = files.stream()
                .filter(file -> file.getName().contains(query))
                .toList();

        Set<MinioObjectDto> foundFolders = findAllMatchingFolders(
                files.stream()
                        .filter(file -> file.getPath().contains(query))
                        .toList(),
                query
        );

        List<MinioObjectDto> results = new ArrayList<>();

        results.addAll(foundFiles);
        results.addAll(foundFolders);

        return results;
    }

    private static Set<MinioObjectDto> findAllMatchingFolders(List<MinioObjectDto> folders, String query) {
        Set<MinioObjectDto> matchingFoldersSet = new HashSet<>();

        for (MinioObjectDto folder : folders) {
            String path = folder.getPath();
            String[] parts = path.split("/");

            StringBuilder currentPath = new StringBuilder();

            for (String part : parts) {
                currentPath.append(part).append("/");
                if (part.contains(query)) {
                    matchingFoldersSet.add(new MinioObjectDto(
                            folder.getOwner(),
                            true,
                            currentPath.toString(),
                            part
                    ));
                }
            }
        }

        return matchingFoldersSet;
    }
}
