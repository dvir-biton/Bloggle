package com.fylora.bloggle

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fylora.auth.presentation.login.LoginScreen
import com.fylora.auth.presentation.signup.SignupScreen
import com.fylora.auth.presentation.splash.SplashScreen
import com.fylora.blog.presentation.feed.FeedScreen
import com.fylora.blog.presentation.notifications.NotificationScreen
import com.fylora.blog.presentation.post.PostScreen
import com.fylora.blog.presentation.profile.ProfileScreen
import com.fylora.blog.presentation.search.SearchScreen
import com.fylora.bloggle.navigation.Route
import com.fylora.core.ui.theme.BloggleTheme
import com.fylora.core.ui.theme.DarkBackground
import com.fylora.core.ui.theme.Yellow
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: SharedPreferences

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = preferences.getString("userId", null)
        setContent {
            BloggleTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }
                var shouldShowBottomBar by remember {
                    mutableStateOf(false)
                }
                var currentRoute by remember {
                    mutableStateOf(Route.SPLASH)
                }

                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    bottomBar = {
                        if(shouldShowBottomBar) {
                            BottomNavBar(
                                onNavigateToProfile = {
                                    currentRoute = Route.PROFILE
                                    navController.navigate(
                                        Route.PROFILE + "/$userId"
                                    )
                                },
                                onNavigateToFeed = {
                                    currentRoute = Route.FEED
                                    navController.navigate(Route.FEED)
                                },
                                onNavigateToNotifications = {
                                    currentRoute = Route.NOTIFICATIONS
                                    navController.navigate(Route.NOTIFICATIONS)
                                },
                                selectedRoute = currentRoute
                            )
                        }
                    }
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
                                        shouldShowBottomBar = true
                                        currentRoute = Route.FEED
                                        navController.navigate(Route.FEED)
                                    }
                                )
                            }
                            composable(Route.LOGIN) {
                                LoginScreen(
                                    snackbarHostState = snackbarHostState,
                                    onSuccess = {
                                        shouldShowBottomBar = true
                                        currentRoute = Route.FEED
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
                                        shouldShowBottomBar = true
                                        currentRoute = Route.FEED
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
                            composable(Route.NOTIFICATIONS) {
                                NotificationScreen(
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

@Composable
fun BottomNavBar(
    onNavigateToProfile: () -> Unit,
    onNavigateToFeed: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    selectedRoute: String
) {
    Box(
        modifier = Modifier
            .background(DarkBackground)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 22.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(
                onClick = onNavigateToProfile
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    tint = if(selectedRoute == Route.PROFILE) {
                        Yellow
                    } else Color.White,
                    modifier = Modifier.scale(2f)
                )
            }
            IconButton(
                onClick = onNavigateToFeed
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Feed",
                    tint = if(selectedRoute == Route.FEED) {
                        Yellow
                    } else Color.White,
                    modifier = Modifier.scale(2f)
                )
            }
            IconButton(
                onClick = onNavigateToNotifications
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = if(selectedRoute == Route.NOTIFICATIONS) {
                        Yellow
                    } else Color.White,
                    modifier = Modifier.scale(2f)
                )
            }
        }
    }
}