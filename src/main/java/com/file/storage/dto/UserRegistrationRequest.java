package com.file.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRegistrationRequest {
    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String email;
}
