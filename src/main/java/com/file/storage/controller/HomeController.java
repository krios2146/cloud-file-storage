package com.file.storage.controller;

import com.file.storage.dto.FileUploadRequest;
import com.file.storage.dto.FolderUploadRequest;
import com.file.storage.dto.MinioObjectDto;
import com.file.storage.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final FileService fileService;

    @GetMapping
    public String showHomePage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("fileUploadRequest", new FileUploadRequest());
        model.addAttribute("folderUploadRequest", new FolderUploadRequest());

        List<MinioObjectDto> userFiles = fileService.getUserFiles(user.getUsername());
        model.addAttribute("files", userFiles);
        return "home";
    }
}
