package com.fylora.blog.data.client

import com.fylora.blog.data.model.Account
import com.fylora.blog.data.model.Comment
import com.fylora.blog.data.model.Notification
import com.fylora.blog.data.model.Post
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Response {
    @Serializable
    @SerialName("respond_post")
    data class PostResponse(val post: Post): Response()
    @Serializable
    @SerialName("respond_posts")
    data class PostsResponse(val posts: List<Post>): Response()
    @Serializable
    @SerialName("respond_comment")
    data class CommentResponse(val comment: Comment): Response()
    @Serializable
    @SerialName("respond_notifications")
    data class NotificationsResponse(val notification: List<Notification>): Response()
    @Serializable
    @SerialName("respond_account")
    data class AccountResponse(val account: Account): Response()
    @Serializable
    @SerialName("respond_accounts")
    data class AccountsResponse(val account: List<Account>): Response()
    @Serializable
    @SerialName("respond_confirmations")
    data class ConfirmationResponse(val confirmation: String): Response()
    @Serializable
    @SerialName("respond_error")
    data class ErrorResponse(val error: String): Response()
}