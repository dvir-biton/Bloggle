package com.fylora.blog.presentation.post

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.fylora.blog.presentation.components.Send
import com.fylora.blog.presentation.components.TextFieldData
import com.fylora.blog.presentation.feed.components.PostComp
import com.fylora.blog.presentation.post.components.CommentComp

@Composable
fun PostScreen(
    viewModel: PostViewModel = hiltViewModel(),
    onNavigateToAccount: (userId: String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        val isLiked = viewModel.userId!! in viewModel.post.userLiked
        item {
            PostComp(
                post = viewModel.post,
                isLiked = isLiked,
                onClick = { }, // already in post
                onLike = {
                    viewModel.onEvent(
                        PostEvent.OnPostLikeClick
                    )
                },
                onProfileClick = {
                    onNavigateToAccount(viewModel.post.authorId)
                }
            )
            Send(
                username = viewModel.username,
                textFieldData = TextFieldData(
                    value = viewModel.commentText.value,
                    onValueChange = {
                        viewModel.onEvent(
                            PostEvent.OnCommentValueChange(
                                body = it
                            )
                        )
                    },
                    hint = "What do you think?",
                    isHintVisible = viewModel.isHintVisible.value,
                    onFocusChange = {
                        viewModel.onEvent(
                            PostEvent.OnCommentFocusChange(
                                focusState = it
                            )
                        )
                    }
                ),
                onProfileClick = {
                    onNavigateToAccount(
                        viewModel.post.authorId
                    )
                },
                onSend = {
                    viewModel.onEvent(
                        PostEvent.OnCommentSend
                    )
                }
            )
        }

        items(
            viewModel.comments.value,
            key = { comment -> comment.commentId }
        ) {
            val isCommentLiked = viewModel.userId in it.userLiked
            it.userLiked.remove(viewModel.userId)
            CommentComp(
                comment = it,
                isLiked = isCommentLiked,
                onLike = {
                    viewModel.onEvent(
                        PostEvent.OnCommentLikeClick(
                            it.commentId
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