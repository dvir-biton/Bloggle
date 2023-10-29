package com.fylora.blog.presentation.feed.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fylora.blog.data.model.Post
import com.fylora.blog.presentation.components.UserComp
import com.fylora.core.ui.font.fontFamily
import com.fylora.core.ui.theme.LightBlue
import com.fylora.core.ui.theme.Red

@Composable
fun PostComp(
    post: Post,
    isLiked: Boolean,
    onClick: () -> Unit,
    onLike: () -> Unit,
    onProfileClick: () -> Unit
) {
    val color by remember {
        mutableStateOf(Post.colors.random())
    }
    var isLikedState by remember {
        mutableStateOf(isLiked)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color)
            .clickable(onClick = onClick),
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 32.dp,
                    vertical = 16.dp
                )
        ) {
            UserComp(
                username = post.authorName,
                timestamp = post.timestamp,
                onClick = onProfileClick
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = post.body,
                color = Color.White,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Visible,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(30.dp))
            Row {
                BottomData(
                    icon = if(isLikedState) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    tint = Red,
                    amount = post.userLiked.size.toString(),
                    onClick = {
                        onLike()
                        isLikedState = !isLikedState
                    }
                )
                Spacer(modifier = Modifier.width(15.dp))
                BottomData(
                    icon = Icons.Default.ChatBubble,
                    tint = LightBlue,
                    amount = post.comments.size.toString(),
                    onClick = {}
                )
            }
        }
    }
}

@Preview
@Composable
fun PostPreview() {
    PostComp(
        post = Post(
            authorId = "Test",
            authorName = "Test",
            body = "What do you call fish with no eyes?? A fsh (hahahahahaha)",
            userLiked = mutableListOf("1", "2", "3", "4", "5", "6"),
            comments = mutableListOf(),
            postId = "Test",
            timestamp = System.currentTimeMillis(),
        ),
        onClick = {},
        onLike = {},
        isLiked = false,
        onProfileClick = {}
    )
}