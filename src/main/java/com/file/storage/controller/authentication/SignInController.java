package com.file.storage.controller.authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/sign-in")
public class SignInController {
    @PostMapping
    public ResponseEntity authenticate() {
        return null;
    }
}
