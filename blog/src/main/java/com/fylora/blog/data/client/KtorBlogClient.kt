package com.fylora.blog.data.client

import com.fylora.blog.data.client.Request.Companion.generateRequest
import com.fylora.blog.data.model.Account
import com.fylora.blog.data.model.Comment
import com.fylora.blog.data.model.Notification
import com.fylora.blog.data.model.Post
import com.fylora.blog.data.model.Resource
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

class KtorBlogClient(
    private val client: HttpClient
): BlogClient {

    private var session: WebSocketSession

    init {
        runBlocking {
            session = client.webSocketSession {
                url("ws://10.100.102.77:8080/connect")
            }
        }
    }

    override suspend fun requestGetNotifications(request: Request.GetNotifications): Resource<List<Notification>> {
        return try {
            val notifications = mutableListOf<Notification>()

            session.outgoing.send(
                Frame.Text(request.generateRequest())
            )
            session.incoming
                .consumeAsFlow()
                .filterIsInstance<Frame.Text>()
                .collect {
                    notifications.add(Json.decodeFromString(it.readText()))
                }
            Resource.Success(notifications)
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun requestMakePost(request: Request.MakePost): Resource<Post> {
        return try {
            lateinit var post: Post

            session.outgoing.send(
                Frame.Text(request.generateRequest())
            )
            session.incoming
                .consumeAsFlow()
                .filterIsInstance<Frame.Text>()
                .collectLatest {
                    post = Json.decodeFromString(it.readText())
                }
            Resource.Success(post)
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun requestMakeComment(request: Request.MakeComment): Resource<Comment> {
        return try {
            lateinit var comment: Comment

            session.outgoing.send(
                Frame.Text(request.generateRequest())
            )
            session.incoming
                .consumeAsFlow()
                .filterIsInstance<Frame.Text>()
                .collectLatest {
                    comment = Json.decodeFromString(it.readText())
                }
            Resource.Success(comment)
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error("Unknown error")
        }
    }

    override suspend fun requestMakeLikePost(request: Request.MakeLikePost) {
        return try {
            session.outgoing.send(
                Frame.Text(request.generateRequest())
            )
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun requestMakeLikeComment(request: Request.MakeLikeComment) {
        return try {
            session.outgoing.send(
                Frame.Text(request.generateRequest())
            )
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun requestMakeFollow(request: Request.MakeFollow) {
        return try {
            session.outgoing.send(
                Frame.Text(request.generateRequest())
            )
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun requestGetPost(request: Request.GetPost): Resource<List<Comment>> {
        return try {
            val comments = mutableListOf<Comment>()

            session.outgoing.send(
                Frame.Text(request.generateRequest())
            )
            session.incoming
                .consumeAsFlow()
                .filterIsInstance<Frame.Text>()
                .collect {
                    comments.add(Json.decodeFromString(it.readText()))
                }
            Resource.Success(comments)
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun requestSearchAccounts(request: Request.SearchAccounts): Resource<List<Account>> {
        return try {
            val accounts = mutableListOf<Account>()

            session.outgoing.send(
                Frame.Text(request.generateRequest())
            )
            session.incoming
                .consumeAsFlow()
                .filterIsInstance<Frame.Text>()
                .collect {
                    accounts.add(Json.decodeFromString(it.readText()))
                }
            Resource.Success(accounts)
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun requestGetAccount(request: Request.GetAccount): Resource<Account> {
        return try {
            lateinit var account: Account

            session.outgoing.send(
                Frame.Text(request.generateRequest())
            )
            session.incoming
                .consumeAsFlow()
                .filterIsInstance<Frame.Text>()
                .collect {
                    account = Json.decodeFromString(it.readText())
                }
            Resource.Success(account)
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun close() {
        session.close()
    }
}