package com.file.storage.controller;

import com.file.storage.dto.FolderDeleteRequest;
import com.file.storage.dto.FolderRenameRequest;
import com.file.storage.dto.FolderUploadRequest;
import com.file.storage.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/folders")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @PostMapping
    public String uploadFolder(@ModelAttribute("folderUploadRequest") FolderUploadRequest folderUploadRequest) {
        folderService.uploadFolder(folderUploadRequest);
        return "redirect:/";
    }

    @PutMapping
    public String renameFolder(@ModelAttribute("folderRenameRequest") FolderRenameRequest folderRenameRequest) {
        folderService.renameFolder(folderRenameRequest);
        return "redirect:/";
    }

    @DeleteMapping
    public String deleteFolder(@ModelAttribute("folderDeleteRequest") FolderDeleteRequest folderDeleteRequest) {
        folderService.deleteFolder(folderDeleteRequest);
        return "redirect:/";
    }
}
