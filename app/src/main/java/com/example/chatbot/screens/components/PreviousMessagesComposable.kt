package com.example.chatbot.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Tab
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.chatbot.db.entity.ChatWithMessages
import com.example.chatbot.viewModel.state.ChatUiState


@Composable
fun PreviousMessagesComposable(
    showOldMessages: Boolean,
    chatUiState: ChatUiState,
    setSelectedChat: (ChatWithMessages?) -> Unit,
    setShowOldMessages: (Boolean) -> Unit,
) {
    if (showOldMessages && chatUiState.oldMessages.isNotEmpty()) {
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        val maxWidth = screenWidth * 0.5f
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .widthIn(max = maxWidth)
                .padding(top = 56.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                itemsIndexed(chatUiState.oldMessages) { index, chatWithMessages ->
                    val firstMessage = chatWithMessages.messages.firstOrNull()

                    firstMessage?.let { message ->
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .clickable {
                                    setSelectedChat(chatWithMessages)
                                    setShowOldMessages(false)
                                }
                        ) {
                            Column(
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "Chat ${index + 1}: ",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                    Text(
                                        text = "Content: ${message.content}",
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}