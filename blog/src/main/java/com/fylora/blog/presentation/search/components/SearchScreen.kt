package com.fylora.blog.presentation.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fylora.blog.presentation.search.SearchEvent
import com.fylora.blog.presentation.search.SearchViewModel
import com.fylora.core.ui.font.fontFamily
import com.fylora.core.ui.theme.DarkBackground
import com.fylora.core.ui.theme.LightGray

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    Column {
        Box(modifier = Modifier.background(DarkBackground)) {
            SearchTextField(
                value = viewModel.query.value,
                isHintVisible = viewModel.isHintVisible.value,
                onValueChange = {
                    viewModel.onEvent(
                        SearchEvent.OnSearchChange(it)
                    )
                },
                onFocusChange = {
                    viewModel.onEvent(
                        SearchEvent.OnFocusChange(it)
                    )
                },
                onSearch = {
                    viewModel.onEvent(
                        SearchEvent.OnSearch
                    )
                },
                modifier = Modifier.padding(
                    vertical = 35.dp,
                    horizontal = 20.dp
                )
            )
        }

        Box(modifier = Modifier.weight(1f)){
            LazyColumn {
                items(
                    items = viewModel.accounts,
                    key = { account -> account.userId }
                ) { account ->
                    AccountComp(username = account.username) {
                        // TODO:
                    }
                }
            }

            if (viewModel.error.value.isNotBlank()) {
                Text(
                    text = viewModel.error.value,
                    textAlign = TextAlign.Center,
                    color = LightGray,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }
        }

    }
}