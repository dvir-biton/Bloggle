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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fylora.auth.presentation.login.LoginScreen
import com.fylora.auth.presentation.signup.SignupScreen
import com.fylora.auth.presentation.splash.SplashScreen
import com.fylora.blog.presentation.feed.FeedScreen
import com.fylora.blog.presentation.post.PostScreen
import com.fylora.blog.presentation.profile.ProfileScreen
import com.fylora.blog.presentation.search.SearchScreen
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
                            startDestination = Route.SPLASH
                        ) {
                            composable(Route.SPLASH) {
                                SplashScreen(
                                    navigateToLogin = {
                                        navController.navigate(Route.LOGIN)
                                    },
                                    onSuccess = {
                                        navController.navigate(Route.FEED)
                                    }
                                )
                            }
                            composable(Route.LOGIN) {
                                LoginScreen(
                                    snackbarHostState = snackbarHostState,
                                    onSuccess = {
                                        navController.navigate(Route.FEED)
                                    },
                                    navToSignUp = {
                                        navController.navigate(Route.SIGNUP)
                                    }
                                )
                            }
                            composable(Route.SIGNUP) {
                                SignupScreen(
                                    snackbarHostState = snackbarHostState,
                                    onSuccess = {
                                        navController.navigate(
                                            Route.FEED
                                        )
                                    },
                                    navToLogin = {
                                        navController.navigate(
                                            Route.LOGIN
                                        )
                                    }
                                )
                            }
                            composable(Route.FEED) {
                                FeedScreen(
                                    onNavigateToSearch = {
                                        navController.navigate(
                                            Route.SEARCH
                                        )
                                    },
                                    onNavigateToPost = { post ->
                                        navController.navigate(
                                            Route.POST + "/$post"
                                        )
                                    },
                                    onNavigateToAccount = { userId ->
                                        navController.navigate(
                                            Route.PROFILE + "/$userId"
                                        )
                                    }
                                )
                            }
                            composable(
                                route = Route.PROFILE + "/{userId}",
                                arguments = listOf(
                                    navArgument("userId") {
                                        type = NavType.StringType
                                    }
                                )
                            ) {
                                ProfileScreen(
                                    onNavigateToPost = { post ->
                                        navController.navigate(
                                            Route.POST + "/$post"
                                        )
                                    },
                                )
                            }
                            composable(
                                route = Route.POST + "/{post}",
                                arguments = listOf(
                                    navArgument("post") {
                                        type = NavType.StringType
                                    }
                                )
                            ) {
                                PostScreen(
                                    onNavigateToAccount = { userId ->
                                        navController.navigate(
                                            Route.PROFILE + "/$userId"
                                        )
                                    }
                                )
                            }
                            composable(Route.SEARCH) {
                                SearchScreen(
                                    onNavigateToAccount = { userId ->
                                        navController.navigate(
                                            Route.PROFILE + "/$userId"
                                        )
                                    }
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}
