package com.example.chatbot.di

import com.example.chatbot.repositories.network.ChatNetworkRepoImpl
import com.example.chatbot.repositories.network.IChatNetworkRepo
import com.example.chatbot.useCases.network.StartChatNetworkUseCase
import com.example.chatbot.viewModel.ChatViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel


val chatNetworkModule = module {
    viewModel {
        ChatViewModel(
            get(),
            get(),
            get(),
            get()
        )
    }
    single<IChatNetworkRepo> { ChatNetworkRepoImpl(get()) }
    single { StartChatNetworkUseCase(get()) }
    single<CoroutineDispatcher> { Dispatchers.IO }
}