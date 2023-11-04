package com.fylora.blog.presentation.notifications

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.fylora.blog.presentation.components.TopBar
import com.fylora.blog.presentation.notifications.components.NotificationComp

@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel = hiltViewModel(),
    onNavigateToAccount: (userId: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            TopBar(title = "Notifications")
        }
        items(
            items = viewModel.notifications.value,
        ) {
            NotificationComp(
                notification = it,
                onNavigateToAccount = {
                    onNavigateToAccount(
                        viewModel.userId
                    )
                }
            )
        }
    }
}