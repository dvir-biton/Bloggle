package com.fylora.blog.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fylora.core.ui.font.fontFamily
import com.fylora.core.ui.theme.DarkBackground

@Composable
fun BloggleTopBar(
    onNavigateToSearch: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkBackground),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 25.dp,
                    vertical = 40.dp
                )
        ) {
            Text(
                text = "Bloggle",
                color = Color.White,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp
            )

            IconButton(
                onClick = onNavigateToSearch
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White,
                    modifier = Modifier.scale(1.8f)
                )
            }
        }
    }
}

@Preview
@Composable
fun BloggleTopBarPrev() {
    BloggleTopBar {}
}