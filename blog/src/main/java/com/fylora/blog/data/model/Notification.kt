package com.fylora.blog.data.model

import kotlinx.serialization.Serializable

@Serializable
sealed class Notification(val message: String) {
    @Serializable
    data class PostLiked(
        val by: String,
        val timestamp: Long,
        val postId: String,
    ): Notification("Liked you post!")
    @Serializable
    data class CommentLiked(
        val by: String,
        val timestamp: Long,
        val postId: String,
    ): Notification("Liked you comment!")
    @Serializable
    data class Following(
        val by: String,
        val timestamp: Long,
    ): Notification("Started following you!")
    @Serializable
    data class Comment(
        val by: String,
        val timestamp: Long,
        val postId: String,
    ): Notification("Commented on you post!")
    @Serializable
    data class NewPost(
        val by: String,
        val timestamp: Long,
        val postId: String,
    ): Notification("Made a new post!\ngo check it out!")
}
