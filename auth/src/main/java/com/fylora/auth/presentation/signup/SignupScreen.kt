package com.fylora.auth.presentation.signup

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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fylora.auth.presentation.UiEvent
import com.fylora.auth.presentation.components.AuthTextField
import com.fylora.core.ui.components.ActionButton
import com.fylora.core.ui.font.fontFamily
import com.fylora.core.ui.theme.LightBlue
import com.fylora.core.ui.theme.Red
import com.fylora.core.ui.theme.Yellow

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignupScreen(
    viewModel: SignupScreenViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    onSuccess: () -> Unit,
    navToLogin: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    var confirmPasswordErrorText by remember {
        mutableStateOf("")
    }
    var passwordErrorText by remember {
        mutableStateOf("")
    }
    var usernameErrorText by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when(it) {
                UiEvent.NavigateToLogin -> navToLogin()
                UiEvent.Success -> onSuccess()
                else -> Unit
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiState.collect {
            when(it) {
                is UiState.ConfirmPasswordTextFieldError ->
                    confirmPasswordErrorText = it.message
                is UiState.PasswordTextFieldError ->
                    passwordErrorText = it.message
                is UiState.UsernameTextFieldError ->
                    usernameErrorText = it.message
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
                    text = "Create an account",
                    fontSize = 36.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    color = Yellow,
                )
                Text(
                    text = "Thank you for joining us!",
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
                            SignupScreenEvent.EnteredUsername(it)
                        )
                    },
                    hint = "Username",
                    isHintVisible = viewModel.usernameText.value.isHintVisible,
                    icon = Icons.Default.Person,
                    onFocusChange = {
                        viewModel.onEvent(
                            SignupScreenEvent.ChangedUsernameFocus(it)
                        )
                    }
                )
                Text(
                    text = usernameErrorText,
                    color = Red,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                AuthTextField(
                    value = viewModel.passwordText.value.text,
                    onValueChange = {
                        viewModel.onEvent(
                            SignupScreenEvent.EnteredPassword(it)
                        )
                    },
                    hint = "Password",
                    isHintVisible = viewModel.passwordText.value.isHintVisible,
                    icon = Icons.Default.Key,
                    onFocusChange = {
                        viewModel.onEvent(
                            SignupScreenEvent.ChangedPasswordFocus(it)
                        )
                    },
                    isPassword = true
                )
                Text(
                    text = passwordErrorText,
                    color = Red,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                AuthTextField(
                    value = viewModel.confirmPasswordText.value.text,
                    onValueChange = {
                        viewModel.onEvent(
                            SignupScreenEvent.EnteredConfirmPassword(it)
                        )
                    },
                    hint = "Confirm password",
                    isHintVisible = viewModel.confirmPasswordText.value.isHintVisible,
                    icon = Icons.Default.Lock,
                    onFocusChange = {
                        viewModel.onEvent(
                            SignupScreenEvent.ChangedConfirmPasswordFocus(it)
                        )
                    },
                    isPassword = true
                )
                Text(
                    text = confirmPasswordErrorText,
                    color = Red,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                ActionButton(
                    text = "Signup",
                    onClick = {
                        confirmPasswordErrorText = ""
                        passwordErrorText = ""
                        usernameErrorText = ""
                        viewModel.onEvent(SignupScreenEvent.OnSignupButtonClick)
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
                    text = "Already have an account?",
                    fontSize = 16.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
                Text(
                    text = "Log in",
                    fontSize = 16.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Yellow,
                    modifier = Modifier.clickable {
                        viewModel.onEvent(SignupScreenEvent.OnLoginScreenClick)
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