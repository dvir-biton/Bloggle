package com.fylora.auth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fylora.core.ui.font.fontFamily
import com.fylora.core.ui.theme.LightGray
import com.fylora.core.ui.theme.TextFieldColor

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    isHintVisible: Boolean,
    icon: ImageVector,
    isPassword: Boolean = false,
    onFocusChange: (FocusState) -> Unit
) {

    var isTextVisible by remember {
        mutableStateOf(false)
    }
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(TextFieldColor)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { focusRequester.requestFocus() }
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 12.dp,
                    vertical = 20.dp
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = hint,
                tint = LightGray
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.weight(1f)) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    keyboardOptions = if(isPassword) KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ) else KeyboardOptions.Default,
                    singleLine = true,
                    textStyle = TextStyle(
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    ),
                    visualTransformation = if(isPassword && !isTextVisible)
                            PasswordVisualTransformation()
                            else VisualTransformation.None,
                    modifier = Modifier
                        .onFocusChanged {
                            onFocusChange(it)
                        }
                        .focusRequester(focusRequester)
                )
                if(isHintVisible) {
                    Text(
                        text = hint,
                        color = LightGray,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            if(isPassword) {
                Icon(
                    imageVector = if(isTextVisible) Icons.Default.VisibilityOff
                            else Icons.Default.Visibility,
                    contentDescription = if(isTextVisible) "Hide password"
                            else "Show password",
                    tint = LightGray,
                    modifier = Modifier.clickable {
                        isTextVisible = !isTextVisible
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun AuthTextFieldPreviewPassword() {
    AuthTextField(
        value = "test",
        onValueChange = {},
        hint = "",
        isHintVisible = false,
        icon = Icons.Default.Person,
        isPassword = true,
        onFocusChange = {}
    )
}

@Preview
@Composable
fun AuthTextFieldPreviewNormal() {
    AuthTextField(
        value = "test",
        onValueChange = {},
        hint = "",
        isHintVisible = false,
        icon = Icons.Default.Person,
        isPassword = false,
        onFocusChange = {}
    )
}

@Preview
@Composable
fun AuthTextFieldPreviewHint() {
    AuthTextField(
        value = "",
        onValueChange = {},
        hint = "password",
        isHintVisible = true,
        icon = Icons.Default.Person,
        isPassword = true,
        onFocusChange = {}
    )
}