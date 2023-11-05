package com.fylora.blog.presentation.feed

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.fylora.blog.presentation.components.BloggleTopBar
import com.fylora.blog.presentation.components.Send
import com.fylora.blog.presentation.components.TextFieldData
import com.fylora.blog.presentation.feed.components.PostComp
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun FeedScreen(
    viewModel: FeedViewModel = hiltViewModel(),
    onNavigateToSearch: () -> Unit,
    onNavigateToPost: (post: String) -> Unit,
    onNavigateToAccount: (userId: String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            BloggleTopBar(
                onNavigateToSearch = onNavigateToSearch,
            )

            Send(
                username = viewModel.username ?: "error",
                textFieldData = TextFieldData(
                    value = viewModel.postText.value,
                    onValueChange = {
                        viewModel.onEvent(
                            FeedEvent.OnPostValueChange(it)
                        )
                    },
                    hint = "What's up?",
                    isHintVisible = viewModel.isHintVisible.value,
                    onFocusChange = {
                        viewModel.onEvent(
                            FeedEvent.OnPostFocusChange(it)
                        )
                    }
                ),
                onProfileClick = {
                    onNavigateToAccount(
                        viewModel.userId ?: "error"
                    )
                },
                onSend = {
                    viewModel.onEvent(
                        FeedEvent.OnPostSend
                    )
                }
            )
        }

        items(
            viewModel.posts.value,
            key = { post -> post.postId }
        ) {
            val isLiked = viewModel.userId in it.userLiked
            PostComp(
                post = it,
                isLiked = isLiked,
                onClick = {
                    onNavigateToPost(
                        Json.encodeToString(it)
                    )
                },
                onLike = {
                    viewModel.onEvent(
                        FeedEvent.OnPostLikeClick(
                            it.postId
                        )
                    )
                },
                onProfileClick = {
                    onNavigateToAccount(
                        it.authorId
                    )
                }
            )
        }
    }
}