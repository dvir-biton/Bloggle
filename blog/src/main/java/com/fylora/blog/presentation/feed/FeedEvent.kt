package com.fylora.blog.presentation.feed

import androidx.compose.ui.focus.FocusState

sealed interface FeedEvent {
    data class OnPostClick(val postId: String): FeedEvent
    data class OnPostLikeClick(val postId: String): FeedEvent
    data class OnPostValueChange(val body: String): FeedEvent
    data class OnPostFocusChange(val focusState: FocusState): FeedEvent
    data object OnNavigateToSearch: FeedEvent
    data object OnPostSend: FeedEvent
}