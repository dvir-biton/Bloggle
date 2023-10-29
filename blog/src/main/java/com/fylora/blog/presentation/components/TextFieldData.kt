package com.fylora.blog.presentation.components

import androidx.compose.ui.focus.FocusState

data class TextFieldData(
    val value: String,
    val onValueChange: (String) -> Unit,
    val hint: String,
    val isHintVisible: Boolean,
    val onFocusChange: (FocusState) -> Unit
)
