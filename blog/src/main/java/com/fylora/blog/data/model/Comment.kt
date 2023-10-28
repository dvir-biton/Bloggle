package com.fylora.blog.data.model

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Comment(
    val authorId: String,
    val authorName: String,
    val body: String,
    val userLiked: MutableList<String> = mutableListOf(),
    val commentId: String = UUID.randomUUID().toString(),
    val timestamp: Long = System.currentTimeMillis(),
)
