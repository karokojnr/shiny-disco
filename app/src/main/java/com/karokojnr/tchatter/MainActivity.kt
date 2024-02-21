package com.karokojnr.tchatter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.karokojnr.tchatter.conversation.ConversationContent
import com.karokojnr.tchatter.conversation.ConversationUiState
import com.karokojnr.tchatter.theme.TChatterTheme
import com.karokojnr.tchatter.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val messagesWithUsers by viewModel.messages.collectAsStateWithLifecycle()

            val currentUiState =
                ConversationUiState(
                    channelName = "Android Apprentice",
                    initialMessages = messagesWithUsers,
                    viewModel = viewModel
                )

            TChatterTheme {
                ConversationContent(
                    currentUiState
                )
            }
        }
    }
}
