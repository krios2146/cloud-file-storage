package com.file.storage.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @UuidGenerator
    val id: UUID?,

    var login: String?,

    var password: String?
) {
    constructor(login: String, password: String) : this(null, login, password)
    constructor() : this(null, null, null)
}