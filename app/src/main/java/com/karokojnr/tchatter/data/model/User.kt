package com.karokojnr.tchatter.data.model

import java.util.UUID

data class User(
    val id: String = UUID.randomUUID().toString(),
    val firstName: String = "",
    val lastName: String = "",
    val fullName: String = "$firstName $lastName"
)