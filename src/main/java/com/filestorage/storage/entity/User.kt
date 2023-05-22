package com.filestorage.storage.entity

data class User(
    val id: Int?,
    var login: String,
    var password: String
) {
    constructor(login: String, password: String): this(null, login, password)
}