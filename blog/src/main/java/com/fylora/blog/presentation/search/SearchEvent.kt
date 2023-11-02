package com.fylora.blog.presentation.search

import androidx.compose.ui.focus.FocusState

sealed interface SearchEvent {
    data class OnUserClick(val userId: String): SearchEvent
    data class OnSearchChange(val query: String): SearchEvent
    data class OnFocusChange(val focus: FocusState): SearchEvent
    data object OnSearch: SearchEvent
}