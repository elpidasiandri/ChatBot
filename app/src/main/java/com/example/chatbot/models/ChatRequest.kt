package com.example.chatbot.models

data class ChatRequest(
    val model: String, // model eg "gpt-3.5-turbo"
    val messages: List<ChatMessage>
)
data class ChatMessage(
    val role: String,  // "user" or "system"or "assistant"
    val content: String
)

data class ChatResponse(
    val id: String,
    val objectType: String,
    val created: Long,
    val choices: List<Choice>
)

data class Choice(
    val message: ChatMessage,
    val finish_reason: String,
    val index: Int
)