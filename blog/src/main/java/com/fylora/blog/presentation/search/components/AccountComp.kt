package com.fylora.blog.presentation.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fylora.blog.data.model.Post
import com.fylora.blog.presentation.components.UserComp

@Composable
fun AccountComp(
    username: String,
    onClick: () -> Unit
) {
    val color = remember {
        Post.colors.random()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(
                color = color
            ),
        contentAlignment = Alignment.Center
    ) {
        UserComp(
            modifier = Modifier
                .padding(vertical = 40.dp),
            username = username,
            userTextSize = 24.sp
        ) {}
    }
}