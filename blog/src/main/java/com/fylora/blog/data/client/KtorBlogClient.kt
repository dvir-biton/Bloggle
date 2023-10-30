package com.fylora.blog.data.client

import android.content.SharedPreferences
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.headers
import io.ktor.client.request.url
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class KtorBlogClient @Inject constructor(
    private val client: HttpClient,
    prefs: SharedPreferences
): BlogClient {

    private var session: WebSocketSession

    private val token = prefs.getString("jwt", null)

    init {
        runBlocking {
            session = client.webSocketSession {
                url("ws://10.100.102.77:8080/connect")

                headers {
                    append("Authorization", "Bearer $token")
                }
            }
        }
    }

    override suspend fun sendRequest(request: Request): Flow<Response> {
        return flow {
            session.outgoing.send(
                Frame.Text(
                    Json.encodeToString(request)
                )
            )
            println("sent request! $request")

            val response = session.incoming
                .consumeAsFlow()
                .filterIsInstance<Frame.Text>()
                .mapNotNull {
                    println("json: ${it.readText()}")
                    Json.decodeFromString<Response>(it.readText())
                }

            emitAll(response)
        }
    }

    override suspend fun close() {
        session.close()
    }
}