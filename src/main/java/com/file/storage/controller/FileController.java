package com.file.storage.controller;

import com.file.storage.dto.FileDeleteRequest;
import com.file.storage.dto.FileUploadRequest;
import com.file.storage.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping
    public String uploadFile(@ModelAttribute("fileUploadRequest") FileUploadRequest fileUploadRequest) {
        fileService.uploadFile(fileUploadRequest);
        return "redirect:/";
    }

    @DeleteMapping
    public String deleteFile(@ModelAttribute("fileDeleteRequest") FileDeleteRequest fileDeleteRequest) {
        fileService.deleteFile(fileDeleteRequest);
        return "redirect:/";
    }
}
