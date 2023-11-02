package com.fylora.blog.presentation.search.components

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.fylora.core.ui.font.fontFamily
import com.fylora.core.ui.theme.LightGray
import com.fylora.core.ui.theme.TextFieldColor

@Composable
fun SearchTextField(
    value: String,
    isHintVisible: Boolean,
    onValueChange: (String) -> Unit,
    onFocusChange: (FocusState) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
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
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = LightGray
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.weight(1f)) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    singleLine = true,
                    textStyle = TextStyle(
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    ),
                    modifier = Modifier
                        .onFocusChanged {
                            onFocusChange(it)
                        }
                        .focusRequester(focusRequester),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onSearch()
                            this.defaultKeyboardAction(ImeAction.Search)
                        }
                    )
                )
                if(isHintVisible) {
                    Text(
                        text = "Search",
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