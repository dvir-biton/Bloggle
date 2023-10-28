package com.fylora.auth.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fylora.auth.presentation.UiEvent
import com.fylora.core.ui.font.fontFamily
import com.fylora.core.ui.theme.Yellow

@Composable
fun SplashScreen(
    viewModel: SplashScreenViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
    onSuccess: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when(it) {
                UiEvent.NavigateToLogin -> navigateToLogin()
                UiEvent.Success -> onSuccess()
                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Yellow),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Bloggle",
            fontSize = 36.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black
        )
    }
}