package com.file.storage.entity.dto.auth

import lombok.AllArgsConstructor
import lombok.Getter

@AllArgsConstructor
@Getter
data class UserRequestDto(
    val login: String,
    val password: String
)