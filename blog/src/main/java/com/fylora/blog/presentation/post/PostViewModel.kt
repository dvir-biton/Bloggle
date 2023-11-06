package com.fylora.blog.presentation.post

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fylora.blog.data.client.BlogClient
import com.fylora.blog.data.client.BlogClientManager
import com.fylora.blog.data.client.Request
import com.fylora.blog.data.client.Response
import com.fylora.blog.data.model.Comment
import com.fylora.blog.data.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val blogClient: BlogClient,
    private val blogClientManager: BlogClientManager,
    savedStateHandle: SavedStateHandle,
    prefs: SharedPreferences
): ViewModel() {

    val userId = prefs.getString("userId", null)

    var commentText = mutableStateOf("")
        private set

    var isHintVisible = mutableStateOf(true)
        private set

    private var postJson = savedStateHandle.get<String>("post")!!
    var post = Json.decodeFromString<Post>(postJson)
    val username = prefs.getString("username", null)!!

    val comments = mutableStateOf(emptyList<Comment>())

    private val _showSnackBar = Channel<String>()
    val showSnackBar = Channel<String>()

    init {
        viewModelScope.launch {
            blogClient.sendRequest(
                Request.GetPost(
                    post.postId
                )
            )
            blogClientManager.responseFlow
                .collect {
                when(it) {
                    is Response.ConfirmationResponse -> {
                        println(
                            "success: ${it.confirmation}"
                        )
                    }
                    is Response.PostResponse -> {
                        post = it.post
                        comments.value = post.comments
                    }
                    is Response.CommentResponse -> {
                        val mutableList = comments.value.toMutableList()
                        mutableList.add(0, it.comment)
                        comments.value = mutableList
                        post.comments.add(0, it.comment)
                    }
                    else -> Unit
                }
            }
        }
    }

    fun onEvent(event: PostEvent) {
        when(event) {
            is PostEvent.OnCommentFocusChange -> {
                isHintVisible.value = !event.focusState.isFocused
                        && commentText.value.isBlank()
            }
            is PostEvent.OnCommentLikeClick -> {
                viewModelScope.launch {
                    blogClient.sendRequest(
                        Request.MakeLikeComment(
                            event.commentId
                        )
                    )
                }
            }
            PostEvent.OnCommentSend -> {
                viewModelScope.launch {
                    blogClient.sendRequest(
                        Request.MakeComment(
                            body = commentText.value,
                            postId = post.postId
                        )
                    )
                }
                commentText.value = ""
            }
            is PostEvent.OnCommentValueChange -> {
                commentText.value = event.body
            }
            PostEvent.OnPostLikeClick -> {
                viewModelScope.launch {
                    blogClient.sendRequest(
                        Request.MakeLikePost(
                            post.postId
                        )
                    )
                }
            }
        }
    }
}