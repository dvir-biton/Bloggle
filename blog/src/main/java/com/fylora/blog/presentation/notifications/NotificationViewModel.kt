package com.fylora.blog.presentation.notifications

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fylora.blog.data.client.BlogClient
import com.fylora.blog.data.client.BlogClientManager
import com.fylora.blog.data.client.Request
import com.fylora.blog.data.client.Response
import com.fylora.blog.data.model.Notification
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val blogClient: BlogClient,
    private val blogClientManager: BlogClientManager,
    preferences: SharedPreferences
): ViewModel() {
    var error = mutableStateOf("")
        private set

    var notifications = mutableStateOf(emptyList<Notification>())
        private set

    val userId = preferences.getString("userId", "error")!!

    init {
        viewModelScope.launch {
            blogClient.sendRequest(
                Request.GetNotifications
            )
            blogClientManager.responseFlow
                .collect {
                    when(it) {
                        is Response.NotificationsResponse -> {
                            notifications.value = it.notification.take(20)
                        }
                        is Response.ErrorResponse -> {
                            error.value = it.error
                            notifications.value = emptyList()
                        }
                        else -> Unit
                    }
                }
        }
    }
}