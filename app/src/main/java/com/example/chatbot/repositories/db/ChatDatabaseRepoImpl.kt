package com.example.chatbot.repositories.db

import com.example.chatbot.db.dao.ChatDao
import com.example.chatbot.db.dao.MessageDao
import com.example.chatbot.db.entity.ChatEntity
import com.example.chatbot.db.entity.ChatWithMessages
import com.example.chatbot.db.entity.MessageEntity
import kotlinx.coroutines.flow.Flow

class ChatDatabaseRepoImpl(
    private val chatDao: ChatDao,
    private val messageDao: MessageDao
) : IChatDatabaseRepo {
    override suspend fun insertChatAndMessages(chat: ChatEntity, messages: List<MessageEntity>) {
        chatDao.insertChat(chat)
        messages.forEach { message ->
            messageDao.insertMessage(message)
        }
    }

    override  fun getChatsWithMessages(): Flow<List<ChatWithMessages>> {
        return chatDao.getAllChats()
    }
}