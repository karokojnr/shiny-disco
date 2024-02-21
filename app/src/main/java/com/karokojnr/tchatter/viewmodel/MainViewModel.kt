package com.karokojnr.tchatter.viewmodel

import android.net.Uri
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karokojnr.tchatter.conversation.Message
import com.karokojnr.tchatter.data.DEFAULT_PUBLIC_ROOM_MESSAGES_COLLECTION_ID
import com.karokojnr.tchatter.data.initialMessages
import com.karokojnr.tchatter.data.model.ChatRoom
import com.karokojnr.tchatter.data.model.MessageUiModel
import com.karokojnr.tchatter.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.util.UUID

class MainViewModel: ViewModel() {
    private val userId = UUID.randomUUID().toString()
    var currentUserId = MutableStateFlow(userId)



    private val _messages: MutableList<MessageUiModel> = initialMessages.toMutableStateList()
    private val _messagesFlow: MutableStateFlow<List<MessageUiModel>>  by lazy {
        MutableStateFlow(emptyList())
    }

    val messages = _messagesFlow.asStateFlow()
    private val emptyChatRoom = ChatRoom(
        id = "public",
        name = "Android Apprentice",
        createdOn = Clock.System.now(),
        messagesCollectionId = DEFAULT_PUBLIC_ROOM_MESSAGES_COLLECTION_ID,
        isPrivate = false,
        collectionID = "public",
        createdBy = "Kodeco User"
    )

    private val _currentChatRoom = MutableStateFlow(emptyChatRoom)
    val currentRoom = _currentChatRoom.asStateFlow()



    fun onCreateNewMessageClick(messageText: String, photoUri: Uri?) {

        val currentMoment: Instant = Clock.System.now()
        val message = Message(
            UUID.randomUUID().toString(),
            currentMoment,
            currentRoom.value.id,
            messageText,
            userId,
            photoUri
        )
        if (message.photoUri == null) {
            viewModelScope.launch(Dispatchers.Default) {
                createMessageForRoom(message, currentRoom.value)
            }
        }
    }
    // 5
    suspend fun createMessageForRoom(message: Message, chatRoom: ChatRoom) {
        // 6
        val user = User(userId)
        val messageUIModel = MessageUiModel(message, user)
        // 7
        _messages.add(0, messageUIModel)
        // 8
        _messagesFlow.emit(_messages)
    }

}