package com.karokojnr.tchatter.conversation

import android.net.Uri
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.toMutableStateList
import com.karokojnr.tchatter.R
import com.karokojnr.tchatter.data.createdOnKey
import com.karokojnr.tchatter.data.dbIdKey
import com.karokojnr.tchatter.data.model.MessageUiModel
import com.karokojnr.tchatter.data.model.toInstant
import com.karokojnr.tchatter.data.roomIdKey
import com.karokojnr.tchatter.data.textKey
import com.karokojnr.tchatter.data.thumbnailKey
import com.karokojnr.tchatter.data.userIdKey
import com.karokojnr.tchatter.viewmodel.MainViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.Instant
import kotlinx.datetime.Clock
import live.ditto.DittoAttachmentToken
import live.ditto.DittoDocument

import java.util.UUID

class ConversationUiState(
    val channelName: String,
    initialMessages: List<MessageUiModel>,
    val viewModel: MainViewModel
) {
    private val _messages: MutableList<MessageUiModel> = initialMessages.toMutableStateList()

    val messages: List<MessageUiModel> = _messages

    //author ID is set to the user ID - it's used to tell if the message is sent from this user (self) when rendering the UI
    val authorId: MutableStateFlow<String> = viewModel.currentUserId

    fun addMessage(msg: String, photoUri: Uri?) {
        viewModel.onCreateNewMessageClick(msg, photoUri, null)
    }
}

@Immutable
data class Message(
    val _id: String = UUID.randomUUID().toString(),
    val createdOn: Instant? = Clock.System.now(),
    val roomId: String = "public", // "public" is the roomID for the default public chat room
    val text: String = "test",
    val userId: String = UUID.randomUUID().toString(),
    val attachmentToken: DittoAttachmentToken?,
    val photoUri: Uri? = null,
    val authorImage: Int = if (userId == "me") R.drawable.profile_photo_android_developer else R.drawable.someone_else
){
    constructor(document: DittoDocument) : this(
        document[dbIdKey].stringValue,
        document[createdOnKey].stringValue.toInstant(),
        document[roomIdKey].stringValue,
        document[textKey].stringValue,
        document[userIdKey].stringValue,
        document[thumbnailKey].attachmentToken
    )
}
