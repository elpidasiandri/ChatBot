package com.example.chatbot.network
import com.theokanning.openai.OpenAiService
import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OpenAiClient( private val openAiService:OpenAiService) {
    suspend fun sendMessage(userMessage: String): String? {
        return withContext(Dispatchers.IO) {
                val message = ChatMessage("user", userMessage)

                val request = ChatCompletionRequest.builder()
                    .model("gpt-3.5-turbo")
                    .messages(listOf(message))
                    .build()

                val response = openAiService.createChatCompletion(request)
                response.choices.firstOrNull()?.message?.content
        }
    }
}

//{
//  "id": "chatcmpl-7oD3JHGzHb48Y8wOgZX3lYXbD3j6Q",
//  "object": "chat.completion",
//  "created": 1638816903,
//  "model": "gpt-3.5-turbo",
//  "choices": [
//    {
//      "message": {
//        "role": "assistant",
//        "content": "Hello, how can I assist you today?"
//      },
//      "finish_reason": "stop",
//      "index": 0
//    }
//  ]
//}