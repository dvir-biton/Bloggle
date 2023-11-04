package com.fylora.blog.presentation.post

import androidx.compose.ui.focus.FocusState

sealed interface PostEvent {
    data class OnCommentLikeClick(val commentId: String): PostEvent
    data class OnCommentValueChange(val body: String): PostEvent
    data class OnCommentFocusChange(val focusState: FocusState): PostEvent
    data object OnCommentSend: PostEvent
    data object OnPostLikeClick: PostEvent
}