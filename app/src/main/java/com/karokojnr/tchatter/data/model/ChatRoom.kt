package com.karokojnr.tchatter.data.model

import androidx.compose.runtime.Immutable
import com.karokojnr.tchatter.data.collectionIdKey
import com.karokojnr.tchatter.data.createdByKey
import com.karokojnr.tchatter.data.createdOnKey
import com.karokojnr.tchatter.data.dbIdKey
import com.karokojnr.tchatter.data.isPrivateKey
import com.karokojnr.tchatter.data.messagesIdKey
import com.karokojnr.tchatter.data.nameKey
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import live.ditto.DittoDocument

@Immutable
data class ChatRoom(
    val id: String,
    val name: String,
    val createdOn: Instant? = Clock.System.now(),
    val messagesCollectionId: String,
    val isPrivate: Boolean = false,
    val collectionID: String?,
    val createdBy: String
) {
    constructor(document: DittoDocument) : this(
        document[dbIdKey].stringValue,
        document[nameKey].stringValue,
        document[createdOnKey].stringValue.toInstant(),
        document[messagesIdKey].stringValue,
        document[isPrivateKey].booleanValue,
        document[collectionIdKey].stringValue,
        document[createdByKey].stringValue,
    )
}