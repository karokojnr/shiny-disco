package com.karokojnr.tchatter.data.model

import com.karokojnr.tchatter.conversation.Message

data class MessageUiModel(
    val message: Message,
    val user: User,
    val id: String = message._id
) {
    companion object {
        operator fun invoke(message: Message, users: List<User>): MessageUiModel {
            var messageSender: User? = null
            for (user in users) {
                if (user.id == message.userId) {
                    messageSender = user
                }
            }
            messageSender?.let {
                return MessageUiModel(user = messageSender, message = message)
            }
            val noUserFound = User()
            return MessageUiModel(user = noUserFound, message = message)
        }
    }

    constructor(message: Message, user: User) : this(
        id = message._id,
        message = message,
        user = user
    )
}
