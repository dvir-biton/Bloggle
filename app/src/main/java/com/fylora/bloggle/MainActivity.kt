package com.fylora.bloggle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fylora.auth.presentation.login.LoginScreen
import com.fylora.bloggle.navigation.Route
import com.fylora.core.ui.theme.BloggleTheme
import com.fylora.core.ui.theme.DarkBackground
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BloggleTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { padding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(DarkBackground)
                            .padding(padding)
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = Route.LOGIN
                        ) {
                            composable(Route.LOGIN) {
                                LoginScreen(
                                    snackbarHostState = snackbarHostState,
                                    onSuccess = {
                                        // TODO: nav to bloggle
                                    },
                                    navToSignUp = {
                                        navController.navigate(Route.SIGNUP)
                                    }
                                )
                            }
                            composable(Route.SIGNUP) {

                            }
                        }
                    }
                }

            }
        }
    }
}
