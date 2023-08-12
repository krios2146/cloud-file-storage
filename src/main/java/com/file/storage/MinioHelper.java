package com.file.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MinioHelper {

    public String getRootFolder() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "user-" + auth.getName() + "-files/";
    }
}
