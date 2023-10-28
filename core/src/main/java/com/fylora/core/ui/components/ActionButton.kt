package com.fylora.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fylora.core.ui.font.fontFamily
import com.fylora.core.ui.theme.Yellow

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    color: Color = Yellow,
    textColor: Color = Color.Black,
    text: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(32.dp))
            .background(color = color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(
                vertical = 15.dp,
                horizontal = 95.dp
            ),
            text = text,
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontFamily = fontFamily,
            fontSize = 20.sp
        )
    }
}

@Preview
@Composable
fun ActionButtonPreview() {
    ActionButton(text = "Login") {}
}