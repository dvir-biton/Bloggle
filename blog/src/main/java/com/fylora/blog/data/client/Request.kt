package com.fylora.blog.data.client

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Request {
    @Serializable
    @SerialName("get_notifications")
    data object GetNotifications: Request()
    @Serializable
    @SerialName("get_posts")
    data object GetPosts: Request()
    @Serializable
    @SerialName("make_post")
    data class MakePost(val body: String): Request()
    @Serializable
    @SerialName("make_comment")
    data class MakeComment(val body: String, val postId: String): Request()
    @Serializable
    @SerialName("make_like_post")
    data class MakeLikePost(val postId: String): Request()
    @Serializable
    @SerialName("make_like_comment")
    data class MakeLikeComment(val commentId: String): Request()
    @Serializable
    @SerialName("make_follow")
    data class MakeFollow(val userId: String): Request()
    @Serializable
    @SerialName("get_post")
    data class GetPost(val postId: String): Request()
    @Serializable
    @SerialName("search_accounts")
    data class SearchAccounts(val query: String, val amount: Int): Request()
    @Serializable
    @SerialName("get_account")
    data class GetAccount(val userId: String): Request()
}
