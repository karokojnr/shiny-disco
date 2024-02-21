package com.karokojnr.tchatter.data.repository

import com.karokojnr.tchatter.conversation.Message
import com.karokojnr.tchatter.data.model.ChatRoom
import com.karokojnr.tchatter.data.model.User
import kotlinx.coroutines.flow.Flow
import live.ditto.DittoAttachment

interface Repository {

    fun getDittoSdkVersion(): String

    // rooms
    fun getAllPublicRooms(): Flow<List<ChatRoom>>

    // messages
    fun getAllMessagesForRoom(chatRoom: ChatRoom): Flow<List<Message>>
    suspend fun createMessageForRoom(
        userId: String,
        message: Message,
        chatRoom: ChatRoom,
        attachment: DittoAttachment?
    )

    // users
    suspend fun addUser(user: User)
    fun getAllUsers(): Flow<List<User>>
    suspend fun saveCurrentUser(userId: String, firstName: String, lastName: String)

    // rooms
    suspend fun createRoom(name: String, isPrivate: Boolean = false, userId: String = "Ditto System")
    suspend fun publicRoomForId(roomId: String): ChatRoom
}