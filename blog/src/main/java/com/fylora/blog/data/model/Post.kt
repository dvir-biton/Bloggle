package com.fylora.blog.data.model

import com.fylora.core.ui.theme.DarkGray
import com.fylora.core.ui.theme.DarkerGray
import com.fylora.core.ui.theme.Gray
import com.fylora.core.ui.theme.MiddleGray
import com.fylora.core.ui.theme.PostColor1
import com.fylora.core.ui.theme.PostColor2
import com.fylora.core.ui.theme.PostColor3
import com.fylora.core.ui.theme.PostColor4
import com.fylora.core.ui.theme.PostColor5
import com.fylora.core.ui.theme.PostColor6
import com.fylora.core.ui.theme.PostColor7
import com.fylora.core.ui.theme.PostColor8
import com.fylora.core.ui.theme.PostColor9
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Post(
    val authorId: String,
    val authorName: String,
    val body: String,
    val userLiked: MutableList<String> = mutableListOf(),
    val comments: MutableList<Comment> = mutableListOf(),
    val postId: String = UUID.randomUUID().toString(),
    val timestamp: Long = System.currentTimeMillis(),
) {
    companion object {
        val colors = listOf(
            DarkGray,
            DarkerGray,
            Gray,
            MiddleGray,
            PostColor1,
            PostColor2,
            PostColor3,
            PostColor4,
            PostColor5,
            PostColor6,
            PostColor7,
            PostColor8,
            PostColor9,
        )
    }
}
