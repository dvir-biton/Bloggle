package com.fylora.blog.presentation.feed

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fylora.blog.data.client.BlogClient
import com.fylora.blog.data.client.BlogClientManager
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
    private val blogClientManager: BlogClientManager,
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
            if(username == null || userId == null) {
                _uiEvent.send(
                    FeedUiEvent.ShowSnackBar(
                        "Error loading user data"
                    )
                )
                prefs.edit {
                    this.remove("jwt")
                }
                return@launch
            }
            blogClient.sendRequest(
                Request.GetPosts
            )
            blogClientManager.responseFlow.collect {
                when(it) {
                    is Response.ConfirmationResponse -> {
                        println(
                            "success: ${it.confirmation}"
                        )
                    }
                    is Response.PostResponse -> {
                        val isPostInList = posts.value.any { post ->
                            post.postId == it.post.postId
                        }
                        if(isPostInList) {
                            val index = posts.value.indexOfFirst { post ->
                                post.postId == it.post.postId
                            }
                            val list = posts.value.toMutableList()
                            list[index] = it.post
                            posts.value = list
                        } else {
                            val mutableList = posts.value.toMutableList()
                            mutableList.add(0, it.post)
                            posts.value = mutableList
                        }
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
                    )
                }
                postText.value = ""
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
                    )
                }
            }
        }
    }
}