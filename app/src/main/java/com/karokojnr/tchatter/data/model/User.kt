package com.karokojnr.tchatter.data.model

import com.karokojnr.tchatter.data.dbIdKey
import com.karokojnr.tchatter.data.firstNameKey
import com.karokojnr.tchatter.data.lastNameKey
import live.ditto.DittoDocument
import java.util.UUID

data class User(
    val id: String = UUID.randomUUID().toString(),
    val firstName: String = "",
    val lastName: String = "",
    val fullName: String = "$firstName $lastName"
) {
    constructor(document: DittoDocument) : this(
        document[dbIdKey].stringValue,
        document[firstNameKey].stringValue,
        document[lastNameKey].stringValue
    )
}