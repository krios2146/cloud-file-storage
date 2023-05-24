package com.file.storage.controller;

import com.file.storage.entity.User;
import com.file.storage.entity.dto.auth.UserRequestDto;
import com.file.storage.entity.dto.auth.UserResponseDto;
import com.file.storage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(UserRequestDto userDto) {
        User user = new User(userDto.getLogin(), userDto.getPassword());

        User saved = userRepository.save(user);

        UserResponseDto userResponseDto = new UserResponseDto(
                saved.getId(),
                saved.getLogin()
        );

        return ResponseEntity.ok(userResponseDto);
    }
}
