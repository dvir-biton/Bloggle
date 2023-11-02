package com.fylora.blog.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fylora.blog.R
import com.fylora.core.ui.font.fontFamily

@Composable
fun UserComp(
    modifier: Modifier = Modifier,
    username: String,
    timestamp: Long? = null,
    userTextSize: TextUnit = 16.sp,
    onClick: () -> Unit
) {
    val date = timestamp?.let { getDateFromMillis(milliseconds = it) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onClick() }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.pfp),
            contentDescription = "User profile picture",
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = username,
                color = Color.White,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = userTextSize
            )
            if(date != null) {
                Text(
                    text = date,
                    color = LightGray,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun UserWithTime() {
    UserComp(
        username = "bidbid",
        timestamp = System.currentTimeMillis()
    ) {}
}

@Preview
@Composable
fun UserWithoutTime() {
    UserComp(
        username = "bidbid",
    ) {}
}