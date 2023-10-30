package com.fylora.blog.data.client

import kotlinx.coroutines.flow.Flow

interface BlogClient {
    suspend fun sendRequest(request: Request): Flow<Response>
    suspend fun close()
}
