package com.file.storage.controller;

import com.file.storage.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Controller
@RequestMapping("/folders")
@RequiredArgsConstructor
public class FolderController {

    private final FileService fileService;

    @PostMapping(consumes = {MULTIPART_FORM_DATA_VALUE})
    public String uploadFolder(@RequestParam("folder") List<MultipartFile> files) {
        fileService.uploadFiles(files);
        return "redirect:/";
    }
}
