package com.fylora.blog.data.client

import kotlinx.coroutines.flow.Flow

interface BlogClient {
    suspend fun getResponse(): Flow<Response>
    suspend fun sendRequest(request: Request)
    suspend fun close()
}
