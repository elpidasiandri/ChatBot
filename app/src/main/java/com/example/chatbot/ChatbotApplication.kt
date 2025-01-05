package com.example.chatbot

import android.app.Application
import com.example.chatbot.di.chatNetworkModule
import com.example.chatbot.di.databaseModule
import com.example.chatbot.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ChatbotApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ChatbotApplication)
            modules(networkModule,databaseModule,chatNetworkModule)
        }
    }
}