package com.example.chatbot.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbot.R
import com.example.chatbot.db.entity.ChatEntity
import com.example.chatbot.db.entity.MessageEntity
import com.example.chatbot.models.ChatMessage
import com.example.chatbot.useCases.db.GetChatsWithMessagesUseCase
import com.example.chatbot.useCases.db.InsertChatMessagesUseCase
import com.example.chatbot.useCases.network.StartChatNetworkUseCase
import com.example.chatbot.utils.catchAndHandleError
import com.example.chatbot.viewModel.state.ChatUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class ChatViewModel(
    private val ioDispatcher: CoroutineDispatcher,
    private val startChatNetwork: StartChatNetworkUseCase,
    private val insertChatMessages: InsertChatMessagesUseCase,
    private val getChatsWithMessages: GetChatsWithMessagesUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ChatUiState())
    val state: StateFlow<ChatUiState>
        get() = _state

    init {
        loadChatsFromRoom()
        restartChat()
    }

    private fun loadChatsFromRoom() {
        viewModelScope.launch(ioDispatcher) {
            getChatsWithMessages().catch {
            }.collectLatest { chatsWithMessages ->
                Log.d("Q12345", " loadChatsFromRoom  $chatsWithMessages")
                _state.update {
                    it.copy(
                        oldMessages = chatsWithMessages
                    )
                }
            }

        }
    }

    fun sendMessage(userMessage: String) {
        viewModelScope.launch() {
            _state.update {
                it.copy(
                    messages = it.messages + ChatMessage(role = "user", content = userMessage)
                )
            }
            withContext(ioDispatcher) {
                startChatNetwork(userMessage).catchAndHandleError { errorMessage, errorCode ->
                    val errorMessageId = when (errorCode) {
                        500 -> {
                            R.string.no_internet_connection
                        }

                        else -> {
                            R.string.something_went_wrong
                        }
                    }
                    _state.update {
                        it.copy(
                            messages = it.messages + ChatMessage(
                                role = "assistant",
                                content = "An error occurred."
                            ),
                            showErrorToast = true,
                            errorMessage = errorMessageId
                        )
                    }
                }.collect() { res ->
                    Log.d("Q12345", " res  $res")

                    val chatbotMessage =
                        res?.choices?.firstOrNull()?.message?.content ?: "No response received."
                    startNewChat("", userMessage = userMessage, chatbotMessage = chatbotMessage)
                    _state.update {
                        it.copy(
                            messages = it.messages + ChatMessage(
                                role = "assistant",
                                content = chatbotMessage
                            ),
                            showErrorToast = false
                        )
                    }
                }
            }
        }
    }

    fun restartChat() {
        _state.update {
            it.copy(
                messages = listOf(
                    ChatMessage(
                        role = "assistant",
                        content = "Hello. How can I help you?"
                    )
                )
            )
        }
    }

    fun startNewChat(title: String, userMessage: String, chatbotMessage: String) {
        val newChatId = UUID.randomUUID().toString()
        val chat = ChatEntity(id = newChatId, title = title)
        val messages = listOf(
            MessageEntity(chatId = newChatId, role = "user", content = userMessage),
            MessageEntity(chatId = newChatId, role = "assistant", content = chatbotMessage)
        )
        Log.d("Q12345", " newChatId  $newChatId")

        viewModelScope.launch(ioDispatcher) {
            flow { emit(insertChatMessages(chat, messages)) }.catch {
                Log.d("Q12345", " startNewChat  error ${it.message}")

            }.collectLatest {}
        }
    }

}