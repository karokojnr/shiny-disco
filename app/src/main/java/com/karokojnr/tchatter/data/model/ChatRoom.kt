package com.karokojnr.tchatter.data.model

import androidx.compose.runtime.Immutable
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

@Immutable
data class ChatRoom(
    val id: String,
    val name: String,
    val createdOn: Instant? = Clock.System.now(),
    val messagesCollectionId: String,
    val isPrivate: Boolean = false,
    val collectionID: String?,
    val createdBy: String
)