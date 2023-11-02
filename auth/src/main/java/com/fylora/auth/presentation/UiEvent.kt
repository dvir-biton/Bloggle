package com.fylora.auth.presentation

sealed interface UiEvent {
    data object Success: UiEvent
    data object NavigateToLogin: UiEvent
    data object NavigateToSignUp: UiEvent
}
