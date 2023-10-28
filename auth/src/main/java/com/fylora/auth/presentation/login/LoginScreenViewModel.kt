package com.fylora.auth.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fylora.auth.data.AuthRepository
import com.fylora.auth.data.AuthResult
import com.fylora.auth.presentation.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    var usernameText = mutableStateOf(TextFieldState())
        private set

    var passwordText = mutableStateOf(TextFieldState())
        private set

    var isLoading = mutableStateOf(false)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    fun onEvent(event: LoginScreenEvent) {
        when(event) {
            is LoginScreenEvent.ChangedPasswordFocus -> {
                passwordText.value = passwordText.value.copy(
                    isHintVisible = !event.focusState.isFocused
                            && passwordText.value.text.isBlank()
                )
            }
            is LoginScreenEvent.ChangedUsernameFocus -> {
                usernameText.value = usernameText.value.copy(
                    isHintVisible = !event.focusState.isFocused
                            && usernameText.value.text.isBlank()
                )
            }
            is LoginScreenEvent.EnteredPassword -> {
                passwordText.value = passwordText.value.copy(
                    text = event.content
                )
            }
            is LoginScreenEvent.EnteredUsername -> {
                usernameText.value = usernameText.value.copy(
                    text = event.content
                )
            }
            LoginScreenEvent.OnLoginButtonClick -> {
                viewModelScope.launch {
                    isLoading.value = true
                    val result = repository.signIn(
                        username = usernameText.value.text,
                        password = passwordText.value.text
                    )
                    isLoading.value = false

                    if(result is AuthResult.Authorized) {
                        _uiEvent.send(UiEvent.Success)
                    }
                    else {
                        _error.send(result.data ?: "Unknown error")
                    }
                }
            }
            LoginScreenEvent.OnSignUpScreenClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateToSignUp)
                }
            }
        }
    }
}