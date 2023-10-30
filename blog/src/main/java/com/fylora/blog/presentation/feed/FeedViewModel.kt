package com.fylora.blog.presentation.feed

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fylora.blog.data.client.BlogClient
import com.fylora.blog.data.client.Request
import com.fylora.blog.data.client.Response
import com.fylora.blog.data.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val blogClient: BlogClient,
    prefs: SharedPreferences
): ViewModel() {

    var postText = mutableStateOf("")
        private set

    var isHintVisible = mutableStateOf(true)
        private set

    var posts = mutableStateOf(emptyList<Post>())
        private set

    val username = prefs.getString("username", null)
    val userId = prefs.getString("userId", null)

    private val _uiEvent = Channel<FeedUiEvent>()
    val uiEvent = Channel<FeedUiEvent>()

    init {
        viewModelScope.launch {
            blogClient.sendRequest(
                Request.GetPosts
            ).collect {
                when(it) {
                    is Response.ErrorResponse -> {
                        _uiEvent.send(
                            FeedUiEvent.ShowSnackBar(
                                it.error
                            )
                        )
                    }
                    is Response.PostsResponse -> {
                        posts.value = it.posts
                    }
                    else -> Unit
                }
            }
        }
    }

    fun onEvent(event: FeedEvent) {
        when(event) {
            FeedEvent.OnNavigateToSearch -> {

            }
            is FeedEvent.OnPostClick -> {

            }
            FeedEvent.OnPostSend -> {
                viewModelScope.launch {
                    blogClient.sendRequest(
                        Request.MakePost(
                            postText.value
                        )
                    ).collect {
                        when(it) {
                            is Response.ErrorResponse -> {
                                println("failed added post")
                                _uiEvent.send(
                                    FeedUiEvent.ShowSnackBar(
                                        it.error
                                    )
                                )
                            }
                            is Response.PostResponse -> {
                                println("added post")
                                posts.value += it.post
                            }
                            else -> {
                                println("else post")
                            }
                        }
                    }
                }
            }
            is FeedEvent.OnPostValueChange ->
                postText.value = event.body

            is FeedEvent.OnPostFocusChange -> {
                isHintVisible.value = !event.focusState.isFocused
                    && postText.value.isBlank()
            }

            is FeedEvent.OnPostLikeClick -> {
                viewModelScope.launch {
                    blogClient.sendRequest(
                        Request.MakeLikePost(
                            event.postId
                        )
                    ).collect {
                        when(it) {
                            is Response.ConfirmationResponse -> println("like!")
                            is Response.ErrorResponse -> {
                                _uiEvent.send(
                                    FeedUiEvent.ShowSnackBar(
                                        it.error
                                    )
                                )
                            }
                            else -> println("nah")
                        }
                    }
                }
            }
        }
    }
}