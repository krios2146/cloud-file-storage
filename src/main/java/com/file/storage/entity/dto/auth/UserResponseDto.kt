package com.file.storage.entity.dto.auth

import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import lombok.Setter
import java.util.*

@AllArgsConstructor
@NoArgsConstructor
@Setter
data class UserResponseDto(
    val id: UUID,
    var login: String
)
