package com.fylora.auth.presentation

sealed interface UiEvent {
    object Success: UiEvent
    object NavigateToLogin: UiEvent
    object NavigateToSignUp: UiEvent
}
