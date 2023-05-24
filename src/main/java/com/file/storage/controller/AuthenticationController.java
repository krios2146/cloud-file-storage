package com.file.storage.controller;

import com.file.storage.entity.dto.auth.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login() {
        return null;
    }

    @PostMapping("/logout")
    public void logout() {

    }
}
