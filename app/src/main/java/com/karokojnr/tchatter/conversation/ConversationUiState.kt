package com.karokojnr.tchatter.conversation

import android.net.Uri
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.toMutableStateList
import com.karokojnr.tchatter.R
import com.karokojnr.tchatter.data.model.MessageUiModel
import kotlinx.datetime.Instant
import kotlinx.datetime.Clock

import java.util.UUID

class ConversationUiState(
    val channelName: String,
    initialMessages: List<MessageUiModel>,
) {
    private val _messages: MutableList<MessageUiModel> = initialMessages.toMutableStateList()

    val messages: List<MessageUiModel> = _messages

    fun addMessage(msg: String, photoUri: Uri?) {
        // TODO : implement in Chapter 6 😀
    }
}

@Immutable
data class Message(
    val _id: String = UUID.randomUUID().toString(),
    val createdOn: Instant? = Clock.System.now(),
    val roomId: String = "public", // "public" is the roomID for the default public chat room
    val text: String = "test",
    val userId: String = UUID.randomUUID().toString(),
    val photoUri: Uri? = null,
    val authorImage: Int = if (userId == "me") R.drawable.profile_photo_android_developer else R.drawable.someone_else
)