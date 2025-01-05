package com.example.chatbot.viewModel.state

import com.example.chatbot.db.entity.ChatWithMessages
import com.example.chatbot.models.ChatMessage

data class ChatUiState(
    val isRefreshing: Boolean = true,
    val userMessage : String = "",
    val messages: List<ChatMessage> = emptyList(),
    val oldMessages: List<ChatWithMessages> = emptyList(),
    val showErrorToast: Boolean = false,
    val errorMessage: Int = -1
    )