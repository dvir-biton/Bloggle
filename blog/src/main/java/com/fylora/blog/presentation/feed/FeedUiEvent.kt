package com.fylora.blog.presentation.feed

sealed interface FeedUiEvent {
    data class ShowSnackBar(val message: String): FeedUiEvent
}