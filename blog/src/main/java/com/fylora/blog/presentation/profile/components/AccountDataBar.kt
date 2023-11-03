package com.fylora.blog.presentation.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fylora.blog.data.model.Account
import com.fylora.core.ui.components.ActionButton

@Composable
fun AccountDataBar(
    account: Account,
    onFollow: () -> Unit,
    isFollowing: Boolean = false,
    isOwnProfile: Boolean = false
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .padding(
                    vertical = 20.dp
                )
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                AccountData(
                    data = "Posts",
                    name = account.posts.size.toString()
                )
                AccountData(
                    data = "Followers",
                    name = account.followers.size.toString()
                )
                AccountData(
                    data = "Likes",
                    name = account.totalLikes.toString()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            ActionButton(
                text = if(isFollowing)
                    "Unfollow"
                else "Follow",
                onClick = onFollow,
                isActive = !isOwnProfile
            )
        }
    }
}