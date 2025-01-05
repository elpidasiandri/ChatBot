package com.example.chatbot.useCases.db

import com.example.chatbot.db.entity.ChatWithMessages
import com.example.chatbot.repositories.db.IChatDatabaseRepo
import kotlinx.coroutines.flow.Flow

class GetChatsWithMessagesUseCase (private val repo: IChatDatabaseRepo){
    operator fun invoke(): Flow<List<ChatWithMessages>> = repo.getChatsWithMessages()
}