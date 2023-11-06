package com.fylora.blog.presentation.profile

import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fylora.blog.data.client.BlogClient
import com.fylora.blog.data.client.BlogClientManager
import com.fylora.blog.data.client.Request
import com.fylora.blog.data.client.Response
import com.fylora.blog.data.model.Account
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val blogClient: BlogClient,
    private val blogClientManager: BlogClientManager,
    preferences: SharedPreferences,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var error = mutableStateOf("")
        private set

    val userId = savedStateHandle.get<String>("userId")

    private val ownUserId = preferences.getString("userId", null)

    val account: MutableState<Account?> = mutableStateOf(null)

    var isFollowing = mutableStateOf(false)
        private set

    var isOwnProfile = mutableStateOf(false)
        private set

    var isLoading = mutableStateOf(true)

    init {
        if(userId == null) {
            error.value = "Unable to get user, please try again later"
        } else {
            viewModelScope.launch {
                blogClient.sendRequest(
                    Request.GetAccount(
                        userId
                    )
                )
                println("viewModel request sent")
                blogClientManager.responseFlow
                    .collect {
                        println("viewmodel response! $it")
                        when(it) {
                            is Response.AccountResponse -> {
                                account.value = it.account
                                isFollowing.value =
                                    ownUserId in account.value!!.followers
                                isOwnProfile.value =
                                    ownUserId == account.value!!.userId
                                isLoading.value = false
                            }
                            is Response.ConfirmationResponse -> {
                                if("Followed" in it.confirmation) {
                                    isFollowing.value = !isFollowing.value
                                }
                            }
                            else -> Unit
                        }
                    }
            }
        }
    }

    fun onLike(postId: String) {
        viewModelScope.launch {
            blogClient.sendRequest(
                Request.MakeLikePost(
                    postId
                )
            )
        }
    }

    fun onFollow() {
        viewModelScope.launch {
            blogClient.sendRequest(
                Request.MakeFollow(
                    userId!!
                )
            )
        }
    }
}