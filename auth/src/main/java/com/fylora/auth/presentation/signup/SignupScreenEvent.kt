package com.fylora.auth.presentation.signup

import androidx.compose.ui.focus.FocusState

sealed interface SignupScreenEvent {
    object OnSignupButtonClick: SignupScreenEvent
    object OnLoginScreenClick: SignupScreenEvent
    data class EnteredUsername(val content: String): SignupScreenEvent
    data class ChangedUsernameFocus(val focusState: FocusState): SignupScreenEvent
    data class EnteredPassword(val content: String): SignupScreenEvent
    data class ChangedPasswordFocus(val focusState: FocusState): SignupScreenEvent
    data class EnteredConfirmPassword(val content: String): SignupScreenEvent
    data class ChangedConfirmPasswordFocus(val focusState: FocusState): SignupScreenEvent
}