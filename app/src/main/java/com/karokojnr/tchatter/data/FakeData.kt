package com.karokojnr.tchatter.data

import com.karokojnr.tchatter.conversation.ConversationUiState
import com.karokojnr.tchatter.conversation.Message
import com.karokojnr.tchatter.data.model.MessageUiModel
import com.karokojnr.tchatter.data.model.User
import kotlinx.datetime.Instant

private val sampleMessageTexts = listOf(
    Message(
        roomId = "Android Apprentice",
        createdOn = Instant.parse("2023-11-16T05:48:01Z"),
        text = "Hey what do you think of this new Android Apprentice book 📖 from https://Kodeco.com ?",
        userId = "other"
    ),
    Message(
        roomId = "Android Apprentice",
        createdOn = Instant.parse("2023-11-17T05:48:01Z"),
        text = "it's pretty 😍 awesome 💯😎. I learned how to make some cool apps including this chat app! 😄🤩🎉",
        userId = "me"
    ),
    Message(
        roomId = "Android Apprentice",
        text = "Wow!",
        userId = "other"
    ),
)

private val meUser = User(id = "me", firstName = "Fuad", lastName = "Kamal")
private val otherUser = User(id = "other", firstName = "Sally", lastName = "Walden")

private val initialMessages = listOf(
    MessageUiModel(message = sampleMessageTexts[0], user = otherUser, id = "0"),
    MessageUiModel(message = sampleMessageTexts[1], user = meUser, id = "1"),
    MessageUiModel(message = sampleMessageTexts[2], user = otherUser, id = "2")
)

val exampleUiState = ConversationUiState(
    initialMessages = initialMessages,
    channelName = "#Android Apprentice",
)