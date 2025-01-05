package com.example.chatbot.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.chatbot.models.ChatMessage
@Composable
fun ChatMessageUi(message: ChatMessage) {
    val isUser = message.role == "user"
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = message.content,
                color = if (isUser) Color.White else Color.Black,
                modifier = Modifier
                    .background(if (isUser) Color(0xFF0F9D58) else Color(0xFFE0E0E0))
                    .padding(12.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}