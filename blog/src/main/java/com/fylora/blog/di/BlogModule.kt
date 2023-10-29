package com.fylora.blog.di

import com.fylora.blog.data.client.BlogClient
import com.fylora.blog.data.client.KtorBlogClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BlogModule {

    @Provides
    @Singleton
    fun provideChatClient(): HttpClient {
        return HttpClient(CIO) {
            install(Logging)
            install(WebSockets)
        }
    }

    @Provides
    @Singleton
    fun provideBlogClient(httpClient: HttpClient): BlogClient {
        return KtorBlogClient(httpClient)
    }
}