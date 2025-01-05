package com.example.chatbot.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.chatbot.db.dao.ChatDao
import com.example.chatbot.db.dao.MessageDao
import com.example.chatbot.db.entity.ChatEntity
import com.example.chatbot.db.entity.MessageEntity

@Database(entities = [ChatEntity::class, MessageEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
    abstract fun chatWithMessagesDao(): MessageDao
}
