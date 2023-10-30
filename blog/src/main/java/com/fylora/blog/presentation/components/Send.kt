package com.fylora.blog.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fylora.blog.data.model.Post

@Composable
fun Send(
    username: String,
    textFieldData: TextFieldData,
    onSend: () -> Unit,
    onProfileClick: () -> Unit,
) {
    val color by remember {
        mutableStateOf(Post.colors.random())
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
                username = username,
                onClick = onProfileClick
            )
            Spacer(modifier = Modifier.height(25.dp))
            SendTextField(
                textFieldData,
                onSend
            )
        }
    }
}

@Preview
@Composable
fun SendPreview() {
    Send(
        username = "Bidbid",
        textFieldData = TextFieldData(
            value = "",
            onValueChange = {},
            hint = "What do you think?",
            isHintVisible = true,
            onFocusChange = {}
        ),
        onSend = {}
    ) {}
}