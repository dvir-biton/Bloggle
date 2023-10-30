package com.fylora.blog.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fylora.core.ui.font.fontFamily
import com.fylora.core.ui.theme.LightGray
import com.fylora.core.ui.theme.Yellow

@Composable
fun SendTextField(
    textFieldData: TextFieldData,
    onSend: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { focusRequester.requestFocus() }
            )
            .wrapContentSize()
            .drawBehind {
                val path = Path().apply {
                    moveTo(0f, size.height)
                    lineTo(size.width, size.height)
                }

                drawPath(
                    path = path,
                    color = LightGray,
                    style = Stroke(
                        width = 0.4.dp.toPx(),
                    )
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 5.dp,
                    vertical = 20.dp
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send",
                tint = Yellow,
                modifier = Modifier.clickable {
                    onSend()
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.weight(1f)) {
                BasicTextField(
                    value = textFieldData.value,
                    onValueChange = textFieldData.onValueChange,
                    singleLine = true,
                    textStyle = TextStyle(
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    ),
                    modifier = Modifier
                        .onFocusChanged {
                            textFieldData.onFocusChange(it)
                        }
                        .focusRequester(focusRequester)
                )
                if(textFieldData.isHintVisible) {
                    Text(
                        text = textFieldData.hint,
                        color = LightGray,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SendTextFieldPreview() {
    SendTextField(
        textFieldData = TextFieldData(
            value = "",
            onValueChange = {},
            hint = "What do you think?",
            isHintVisible = true,
            onFocusChange = {}
        )
    ) {}
}