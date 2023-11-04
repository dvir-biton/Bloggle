package com.fylora.blog.presentation.notifications.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fylora.blog.data.model.Notification
import com.fylora.blog.data.model.Post
import com.fylora.blog.presentation.components.UserComp
import com.fylora.core.ui.font.fontFamily

@Composable
fun NotificationComp(
    notification: Notification,
    onNavigateToAccount: () -> Unit
) {
    val message = remember { notification.message }
    val color = remember { Post.colors.random() }
    var byUser by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    var timestamp by remember { mutableLongStateOf(0L) }

    LaunchedEffect(key1 = true) {
        when(notification) {
            is Notification.Comment -> {
                byUser = notification.by
                id = notification.postId
                timestamp = notification.timestamp
            }
            is Notification.CommentLiked -> {
                byUser = notification.by
                id = notification.postId
                timestamp = notification.timestamp
            }
            is Notification.Following -> {
                byUser = notification.by
                timestamp = notification.timestamp
            }
            is Notification.NewPost -> {
                byUser = notification.by
                id = notification.postId
                timestamp = notification.timestamp
            }
            is Notification.PostLiked -> {
                byUser = notification.by
                id = notification.postId
                timestamp = notification.timestamp
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserComp(
                username = byUser,
                onClick = onNavigateToAccount
            )
            Text(
                text = message,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        }
    }
}

@Preview
@Composable
fun NotificationPrev() {
    NotificationComp(
        notification = Notification.Following(
            "bidbidi1",
            System.currentTimeMillis()
        )
    ) {

    }
}