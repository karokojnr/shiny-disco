package com.karokojnr.tchatter.viewmodel

import android.net.Uri
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karokojnr.tchatter.conversation.Message
import com.karokojnr.tchatter.data.DEFAULT_PUBLIC_ROOM_MESSAGES_COLLECTION_ID
import com.karokojnr.tchatter.data.model.ChatRoom
import com.karokojnr.tchatter.data.model.MessageUiModel
import com.karokojnr.tchatter.data.model.User
import com.karokojnr.tchatter.data.repository.RepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import live.ditto.DittoAttachmentToken
import java.util.UUID

class MainViewModel : ViewModel() {
    // 1
    private val userId = UUID.randomUUID().toString()
    var currentUserId = MutableStateFlow(userId)
    private var firstName: String = ""
    private var lastName: String = ""
    // 2
    private val repository = RepositoryImpl.getInstance()
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

    // 3
    val roomMessagesWithUsersFlow: Flow<List<MessageUiModel>> = combine(
        repository.getAllUsers(),
        repository.getAllMessagesForRoom(currentRoom.value)
    ) { users: List<User>, messages:List<Message> ->

        messages.map {
            MessageUiModel.invoke(
                message = it,
                users = users
            )
        }
    }
    // 4
    init {
        // user initialization - we use the device name for the user's name
        val firstName = "My"
        val lastName = android.os.Build.MODEL
        updateUserInfo(firstName, lastName)
    }

    fun updateUserInfo(firstName: String = this.firstName, lastName: String = this.lastName) {
        viewModelScope.launch {
            repository.saveCurrentUser(userId, firstName, lastName)
        }
    }
    // 5
    fun onCreateNewMessageClick(messageText: String, photoUri: Uri?, attachmentToken: DittoAttachmentToken?) {
        val currentMoment: Instant = Clock.System.now()
        val message = Message(
            UUID.randomUUID().toString(),
            currentMoment,
            currentRoom.value.id,
            messageText,
            userId,
            attachmentToken,
            photoUri
        )

        if (message.photoUri == null) {
            viewModelScope.launch(Dispatchers.Default) {
                repository.createMessageForRoom(userId, message, currentRoom.value, null)
            }
        }
    }
}
