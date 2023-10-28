package com.fylora.blog.data.model

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
)
