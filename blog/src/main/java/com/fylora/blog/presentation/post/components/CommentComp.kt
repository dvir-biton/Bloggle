package com.fylora.blog.presentation.post.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fylora.blog.data.model.Comment
import com.fylora.blog.data.model.Post
import com.fylora.blog.presentation.components.UserComp
import com.fylora.blog.presentation.feed.components.BottomData
import com.fylora.core.ui.font.fontFamily
import com.fylora.core.ui.theme.Red

@Composable
fun CommentComp(
    comment: Comment,
    isLiked: Boolean,
    onLike: () -> Unit,
    onProfileClick: () -> Unit
) {
    val color = remember {
        Post.colors.random()
    }
    var isLikedState by remember {
        mutableStateOf(isLiked)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color),
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 32.dp,
                    vertical = 16.dp
                )
        ) {
            UserComp(
                username = comment.authorName,
                timestamp = comment.timestamp,
                onClick = onProfileClick
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = comment.body,
                color = Color.White,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Visible,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(30.dp))
            BottomData(
                icon = if(isLikedState) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                tint = Red,
                amount = if(isLikedState) (comment.userLiked.size + 1).toString()
                else comment.userLiked.size.toString(),
                onClick = {
                    isLikedState = !isLikedState
                    onLike()
                }
            )
        }
    }
}
