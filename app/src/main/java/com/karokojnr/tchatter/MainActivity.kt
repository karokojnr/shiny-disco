package com.karokojnr.tchatter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.karokojnr.tchatter.conversation.ConversationContent
import com.karokojnr.tchatter.data.exampleUiState
import com.karokojnr.tchatter.theme.TChatterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TChatterTheme {
                ConversationContent(
                    uiState = exampleUiState
                )
            }
        }
    }
}
