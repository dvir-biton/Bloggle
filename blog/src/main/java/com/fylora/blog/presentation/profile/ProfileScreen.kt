package com.fylora.blog.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fylora.blog.presentation.components.TopBar
import com.fylora.blog.presentation.feed.components.PostComp
import com.fylora.blog.presentation.profile.components.AccountDataBar
import com.fylora.blog.presentation.profile.components.UserProfile
import com.fylora.core.ui.font.fontFamily
import com.fylora.core.ui.theme.DarkBackground
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigateToPost: (post: String) -> Unit
) {
    if(
        viewModel.isLoading.value
        && viewModel.error.value.isBlank()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        val account = remember {
            viewModel.account.value
        }
        Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkBackground)
                ) {
            TopBar(title = "Profile")

            Box(
                modifier = Modifier.weight(1f)
            ) {
                if (viewModel.error.value.isNotBlank()) {
                    Text(
                        text = viewModel.error.value,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.error,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                } else {
                    LazyColumn {
                        item {
                            UserProfile(
                                username = account!!.username
                            )
                            AccountDataBar(
                                account = account,
                                onFollow = viewModel::onFollow,
                                isFollowing = viewModel.isFollowing.value,
                                isOwnProfile = viewModel.isOwnProfile.value
                            )
                        }

                        items(
                            items = account!!.posts,
                            key = { post -> post.postId }
                        ) {
                            val isLiked = viewModel.userId in it.userLiked
                            it.userLiked.remove(viewModel.userId)

                            PostComp(
                                post = it,
                                isLiked = isLiked,
                                onClick = {
                                    onNavigateToPost(
                                        Json.encodeToString(it)
                                    )
                                },
                                onLike = {
                                    viewModel.onLike(it.postId)
                                },
                                onProfileClick = {} // already in profile
                            )
                        }
                    }
                }
            }
        }
    }
}