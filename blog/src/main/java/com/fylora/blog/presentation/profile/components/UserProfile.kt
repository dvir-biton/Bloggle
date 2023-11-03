package com.fylora.blog.presentation.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fylora.blog.R
import com.fylora.blog.data.model.Post
import com.fylora.core.ui.font.fontFamily

@Composable
fun UserProfile(
    username: String
) {
    val color = remember {
        Post.colors.random()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(
                vertical = 25.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.pfp_100,
                ),
                contentDescription = "Profile picture"
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = username,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = fontFamily,
                fontSize = 36.sp
            )
        }
    }
}