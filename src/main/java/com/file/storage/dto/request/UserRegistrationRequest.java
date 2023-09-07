package com.file.storage.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRegistrationRequest {

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 5, message = "Password must be longer than 5 symbols")
    private String password;

    @NotBlank
    @Email
    private String email;
}
