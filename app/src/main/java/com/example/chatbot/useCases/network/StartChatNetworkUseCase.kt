package com.example.chatbot.useCases.network

import com.example.chatbot.repositories.network.IChatNetworkRepo

class StartChatNetworkUseCase(private val repo: IChatNetworkRepo) {
    suspend operator fun invoke(userMessage: String) =
        repo.startChat(userMessage)
}