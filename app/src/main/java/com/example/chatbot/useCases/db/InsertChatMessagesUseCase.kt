package com.example.chatbot.useCases.db

import com.example.chatbot.db.entity.ChatEntity
import com.example.chatbot.db.entity.MessageEntity
import com.example.chatbot.repositories.db.IChatDatabaseRepo

class InsertChatMessagesUseCase(private val repo: IChatDatabaseRepo) {
    suspend operator fun invoke(chat: ChatEntity, messages: List<MessageEntity>) =
        repo.insertChatAndMessages(chat,messages)
}