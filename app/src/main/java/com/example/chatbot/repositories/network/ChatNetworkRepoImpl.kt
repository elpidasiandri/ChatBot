package com.example.chatbot.repositories.network

import com.example.chatbot.models.ChatMessage
import com.example.chatbot.models.ChatRequest
import com.example.chatbot.models.ChatResponse
import com.example.chatbot.models.Choice
import com.example.chatbot.network.OpenAiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChatNetworkRepoImpl(private val client: OpenAiClient): IChatNetworkRepo {
    override suspend fun startChat(userMessage: String): Flow<ChatResponse?> {
       return flow {
//           try {
               val chatRequest = ChatRequest(
                   model = "gpt-3.5-turbo", messages = listOf(ChatMessage("user", userMessage))
               )
               val response = client.sendMessage(chatRequest.messages.first().content)
               emit(
                   ChatResponse(
                       id = "manual",
                       objectType = "chat.completion",
                       created = System.currentTimeMillis(),
                       choices = listOf(
                           Choice(
                               index = 0,
                               message = ChatMessage("assistant", response ?: ""),
                               finish_reason = "stop"
                           )
                       )
                   )
               )
//           }
//           } catch (e: Exception) {
//               emit(null)
//           }
       }
    }
}
//