package com.karokojnr.tchatter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.karokojnr.tchatter.DittoHandler.Companion.ditto
import com.karokojnr.tchatter.conversation.ConversationContent
import com.karokojnr.tchatter.conversation.ConversationUiState
import com.karokojnr.tchatter.data.model.MessageUiModel
import com.karokojnr.tchatter.theme.TChatterTheme
import com.karokojnr.tchatter.viewmodel.MainViewModel
import live.ditto.BuildConfig
import live.ditto.Ditto
import live.ditto.DittoIdentity
import live.ditto.DittoLogLevel
import live.ditto.DittoLogger
import live.ditto.android.DefaultAndroidDittoDependencies
import live.ditto.transports.DittoSyncPermissions

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val messagesWithUsers: List<MessageUiModel> by viewModel
                .roomMessagesWithUsersFlow
                .collectAsStateWithLifecycle(initialValue = emptyList())


            val currentUiState =
                ConversationUiState(
                    channelName = "Android Apprentice",
                    initialMessages = messagesWithUsers.asReversed(),
                    viewModel = viewModel
                )


            TChatterTheme {
                ConversationContent(
                    currentUiState
                )
            }
        }
        checkPermissions()
        setupDitto()
    }

    private fun checkPermissions() {
        val missing = DittoSyncPermissions(this).missingPermissions()
        if (missing.isNotEmpty()) {
            this.requestPermissions(missing, 0)
        }
    }

    private fun setupDitto() {
        val androidDependencies = DefaultAndroidDittoDependencies(applicationContext)
        DittoLogger.minimumLogLevel = DittoLogLevel.DEBUG
        ditto = Ditto(
            androidDependencies,
            DittoIdentity.OnlinePlayground(
                androidDependencies,
                appId = BuildConfig.DITTO_APP_ID,
                token = BuildConfig.DITTO_TOKEN
            )
        )
        ditto.startSync()
    }


}
