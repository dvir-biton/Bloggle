package com.fylora.auth.presentation.signup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fylora.auth.data.AuthRepository
import com.fylora.auth.data.AuthResult
import com.fylora.auth.domain.use_case.ValidatePasswordUseCase
import com.fylora.auth.domain.use_case.ValidateUsernameUseCase
import com.fylora.auth.domain.use_case.ValidationResult
import com.fylora.auth.presentation.TextFieldState
import com.fylora.auth.presentation.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupScreenViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val validateUsernameUseCase: ValidateUsernameUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
): ViewModel() {

    var usernameText = mutableStateOf(TextFieldState())
        private set

    var passwordText = mutableStateOf(TextFieldState())
        private set

    var confirmPasswordText = mutableStateOf(TextFieldState())
        private set

    var isLoading = mutableStateOf(false)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _uiState = Channel<UiState>()
    val uiState = _uiState.receiveAsFlow()

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    init {
        viewModelScope.launch {
            val result = repository.authenticate()

            if(result is AuthResult.Authorized){
                _uiEvent.send(
                    UiEvent.Success
                )
            }
        }
    }


    fun onEvent(event: SignupScreenEvent) {
        when(event) {
            is SignupScreenEvent.ChangedConfirmPasswordFocus -> {
                confirmPasswordText.value = confirmPasswordText.value.copy(
                    isHintVisible = !event.focusState.isFocused
                            && confirmPasswordText.value.text.isBlank()
                )
            }
            is SignupScreenEvent.ChangedPasswordFocus -> {
                passwordText.value = passwordText.value.copy(
                    isHintVisible = !event.focusState.isFocused
                            && passwordText.value.text.isBlank()
                )
            }
            is SignupScreenEvent.ChangedUsernameFocus -> {
                usernameText.value = usernameText.value.copy(
                    isHintVisible = !event.focusState.isFocused
                            && usernameText.value.text.isBlank()
                )
            }
            is SignupScreenEvent.EnteredPassword -> {
                passwordText.value = passwordText.value.copy(
                    text = event.content
                )
            }
            is SignupScreenEvent.EnteredUsername -> {
                usernameText.value = usernameText.value.copy(
                    text = event.content
                )
            }
            is SignupScreenEvent.EnteredConfirmPassword -> {
                confirmPasswordText.value = confirmPasswordText.value.copy(
                    text = event.content
                )
            }
            SignupScreenEvent.OnLoginScreenClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateToLogin)
                }
            }
            SignupScreenEvent.OnSignupButtonClick -> {
                viewModelScope.launch {
                    val passwordResult = validatePasswordUseCase(passwordText.value.text)
                    val usernameResult = validateUsernameUseCase(usernameText.value.text)
                    val isConfirmPasswordMatches = passwordText.value.text == confirmPasswordText.value.text
                    var isFailure = false

                    if(usernameResult is ValidationResult.Error) {
                        _uiState.send(
                            UiState.UsernameTextFieldError(
                                usernameResult.message ?: "Invalid password"
                            )
                        )
                        isFailure = true
                    }
                    if(passwordResult is ValidationResult.Error) {
                        _uiState.send(
                            UiState.PasswordTextFieldError(
                                passwordResult.message ?: "Invalid password"
                            )
                        )
                        isFailure = true
                    }
                    if(!isConfirmPasswordMatches) {
                        _uiState.send(
                            UiState.ConfirmPasswordTextFieldError(
                                "The passwords do not match"
                            )
                        )
                        isFailure = true
                    }
                    if(isFailure)
                        return@launch

                    isLoading.value = true
                    val result = repository.signUp(
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
        }
    }
}