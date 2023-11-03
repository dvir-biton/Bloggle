package com.fylora.blog.presentation.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.fylora.core.ui.font.fontFamily

@Composable
fun AccountData(
    data: String,
    name: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = data,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontFamily = fontFamily,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = name,
            color = Color.White,
            fontWeight = FontWeight.ExtraLight,
            fontFamily = fontFamily,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}