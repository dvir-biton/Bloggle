package com.fylora.blog.data.model

import com.fylora.core.ui.theme.DarkGray
import com.fylora.core.ui.theme.DarkerGray
import com.fylora.core.ui.theme.Gray
import com.fylora.core.ui.theme.MiddleGray
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
            MiddleGray
        )
    }
}
