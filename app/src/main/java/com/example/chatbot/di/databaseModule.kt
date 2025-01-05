package com.example.chatbot.di

import androidx.room.Room
import com.example.chatbot.db.AppDatabase
import com.example.chatbot.repositories.db.ChatDatabaseRepoImpl
import com.example.chatbot.repositories.db.IChatDatabaseRepo
import com.example.chatbot.useCases.db.GetChatsWithMessagesUseCase
import com.example.chatbot.useCases.db.InsertChatMessagesUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "database-name"
        ).build()
    }
    single { get<AppDatabase>().chatDao() }
    single { get<AppDatabase>().chatWithMessagesDao() }
    single<IChatDatabaseRepo> { ChatDatabaseRepoImpl(get(), get()) }
    single { InsertChatMessagesUseCase(get()) }
    single { GetChatsWithMessagesUseCase(get()) }
}