package com.fylora.auth.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fylora.auth.presentation.UiEvent
import com.fylora.auth.presentation.components.AuthTextField
import com.fylora.core.ui.components.ActionButton
import com.fylora.core.ui.font.fontFamily
import com.fylora.core.ui.theme.LightBlue
import com.fylora.core.ui.theme.Yellow

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    onSuccess: () -> Unit,
    navToSignUp: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when(it) {
                UiEvent.NavigateToSignUp -> navToSignUp()
                UiEvent.Success -> onSuccess()
                else -> Unit
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.error.collect {
            snackbarHostState.showSnackbar(it)
        }
    }

    if(!viewModel.isLoading.value){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { focusManager.clearFocus() }
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 90.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome!",
                    fontSize = 36.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    color = Yellow,
                )
                Text(
                    text = "Login",
                    fontSize = 24.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    color = LightBlue,
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AuthTextField(
                    value = viewModel.usernameText.value.text,
                    onValueChange = {
                        viewModel.onEvent(
                            LoginScreenEvent.EnteredUsername(it)
                        )
                    },
                    hint = "Username",
                    isHintVisible = viewModel.usernameText.value.isHintVisible,
                    icon = Icons.Default.Person,
                    onFocusChange = {
                        viewModel.onEvent(
                            LoginScreenEvent.ChangedUsernameFocus(it)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(30.dp))
                AuthTextField(
                    value = viewModel.passwordText.value.text,
                    onValueChange = {
                        viewModel.onEvent(
                            LoginScreenEvent.EnteredPassword(it)
                        )
                    },
                    hint = "Password",
                    isHintVisible = viewModel.passwordText.value.isHintVisible,
                    icon = Icons.Default.Key,
                    onFocusChange = {
                        viewModel.onEvent(
                            LoginScreenEvent.ChangedPasswordFocus(it)
                        )
                    },
                    isPassword = true
                )
                Spacer(modifier = Modifier.height(30.dp))
                ActionButton(
                    text = "Login",
                    onClick = {
                        viewModel.onEvent(LoginScreenEvent.OnLoginButtonClick)
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                )
            }

            Column(
                modifier = Modifier
                    .padding(45.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Don't have an account yet?",
                    fontSize = 16.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
                Text(
                    text = "Sign up",
                    fontSize = 16.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Yellow,
                    modifier = Modifier.clickable {
                        viewModel.onEvent(LoginScreenEvent.OnSignUpScreenClick)
                    }
                )
            }
        }
    }

    if(viewModel.isLoading.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}