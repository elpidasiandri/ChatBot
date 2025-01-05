package com.example.chatbot.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.UUID

@Entity(tableName = "chats")
data class ChatEntity(
    @PrimaryKey val id: String = "",
    val title: String,
    val timestamp: Long = System.currentTimeMillis(),
)

