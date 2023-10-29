package com.fylora.auth.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fylora.auth.data.AuthRepository
import com.fylora.auth.data.AuthResult
import com.fylora.auth.presentation.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    repository: AuthRepository
): ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            async { repository.getInfo() }.await()
            val result = repository.authenticate()

            if(result is AuthResult.Authorized){
                _uiEvent.send(
                    UiEvent.Success
                )
            } else {
                _uiEvent.send(
                    UiEvent.NavigateToLogin
                )
            }
        }
    }
}