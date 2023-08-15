package com.file.storage.controller;

import com.file.storage.dto.*;
import com.file.storage.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.file.storage.BreadcrumbsUtils.getBreadcrumbLinksForPath;
import static com.file.storage.BreadcrumbsUtils.getFolderNamesForPath;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final FileService fileService;

    @GetMapping
    public String showHomePage(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "path", required = false, defaultValue = "") String path,
            Model model) {
        // TODO: path validation
        if (!path.isEmpty() && !path.endsWith("/")) {
            path += "/";
        }
        model.addAttribute("breadcrumbLinks", getBreadcrumbLinksForPath(path));
        model.addAttribute("breadcrumbFolders", getFolderNamesForPath(path));

        model.addAttribute("username", user.getUsername());

        model.addAttribute("fileUploadRequest", new FileUploadRequest());
        model.addAttribute("folderUploadRequest", new FolderUploadRequest());
        model.addAttribute("fileDeleteRequest", new FileDeleteRequest());
        model.addAttribute("folderDeleteRequest", new FolderDeleteRequest());

        List<MinioObjectDto> userFiles = fileService.getUserFiles(user.getUsername(), path);
        model.addAttribute("files", userFiles);

        return "home";
    }
}
