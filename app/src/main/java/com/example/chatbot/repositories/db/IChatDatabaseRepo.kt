package com.example.chatbot.repositories.db

import com.example.chatbot.db.entity.ChatEntity
import com.example.chatbot.db.entity.ChatWithMessages
import com.example.chatbot.db.entity.MessageEntity
import kotlinx.coroutines.flow.Flow

interface IChatDatabaseRepo {
    suspend fun insertChatAndMessages(chat: ChatEntity, messages: List<MessageEntity>)
    fun getChatsWithMessages(): Flow<List<ChatWithMessages>>
}