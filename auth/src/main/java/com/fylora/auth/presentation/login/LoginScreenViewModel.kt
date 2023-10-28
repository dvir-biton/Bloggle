package com.fylora.auth.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fylora.auth.domain.use_case.ValidatePasswordUseCase
import com.fylora.auth.domain.use_case.ValidateUsernameUseCase
import com.fylora.auth.domain.use_case.ValidationResult
import com.fylora.auth.presentation.UiEvent
import com.fylora.auth.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateUsernameUseCase: ValidateUsernameUseCase
): ViewModel() {

    var usernameText = mutableStateOf(TextFieldState())
        private set

    var passwordText = mutableStateOf(TextFieldState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _uiState = Channel<UiState>()
    val uiState = _uiState.receiveAsFlow()

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
                val isPasswordValid = validatePasswordUseCase(passwordText.value.text)
                val isUsernameValid = validateUsernameUseCase(usernameText.value.text)

                viewModelScope.launch {
                    if(isPasswordValid is ValidationResult.Error){
                        _uiState.send(
                            UiState.PasswordTextFieldError(
                                isPasswordValid.message ?: "Invalid password"
                            )
                        )
                        return@launch
                    }
                    if(isUsernameValid is ValidationResult.Error) {
                        _uiState.send(
                            UiState.UsernameTextFieldError(
                                isUsernameValid.message ?: "Invalid username"
                            )
                        )
                        return@launch
                    }
                    _uiEvent.send(UiEvent.Success)
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