package com.example.chatbot.repositories.network

import com.example.chatbot.models.ChatRequest
import com.example.chatbot.models.ChatResponse
import kotlinx.coroutines.flow.Flow

interface IChatNetworkRepo {
    suspend fun startChat(userMessage: String): Flow<ChatResponse?>
}