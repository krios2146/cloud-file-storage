package com.file.storage.controller;

import com.file.storage.entity.User;
import com.file.storage.entity.dto.auth.UserRequestDto;
import com.file.storage.entity.dto.auth.UserResponseDto;
import com.file.storage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto userDto) {
        User user = new User(userDto.getLogin(), userDto.getPassword());

        // TODO: 24-May-23 Extract to service + use ModelMapper
        User saved = userRepository.save(user);

        UserResponseDto userResponseDto = new UserResponseDto(
                saved.getId(),
                saved.getLogin()
        );

        return ResponseEntity.status(CREATED).body(userResponseDto);
    }
}
