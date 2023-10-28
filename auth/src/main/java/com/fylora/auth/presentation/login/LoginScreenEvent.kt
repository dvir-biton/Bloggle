package com.fylora.auth.presentation.login

import androidx.compose.ui.focus.FocusState

sealed interface LoginScreenEvent {
    object OnLoginButtonClick: LoginScreenEvent
    object OnSignUpScreenClick: LoginScreenEvent
    data class EnteredUsername(val content: String): LoginScreenEvent
    data class ChangedUsernameFocus(val focusState: FocusState): LoginScreenEvent
    data class EnteredPassword(val content: String): LoginScreenEvent
    data class ChangedPasswordFocus(val focusState: FocusState): LoginScreenEvent
}