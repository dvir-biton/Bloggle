package com.fylora.blog.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Notification(val message: String) {
    @Serializable
    @SerialName("post_liked_notification")
    data class PostLiked(
        val by: String,
        val timestamp: Long,
        val postId: String,
    ): Notification("Liked you post!")
    @Serializable
    @SerialName("comment_liked_notification")
    data class CommentLiked(
        val by: String,
        val timestamp: Long,
        val postId: String,
    ): Notification("Liked you comment!")
    @Serializable
    @SerialName("follow_notification")
    data class Following(
        val by: String,
        val timestamp: Long,
    ): Notification("Started following you!")
    @Serializable
    @SerialName("comment_notification")
    data class Comment(
        val by: String,
        val timestamp: Long,
        val postId: String,
    ): Notification("Commented on you post!")
    @Serializable
    @SerialName("post_notification")
    data class NewPost(
        val by: String,
        val timestamp: Long,
        val postId: String,
    ): Notification("Made a new post!\ngo check it out!")
}
