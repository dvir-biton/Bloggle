package com.fylora.blog.data.client

sealed class Request(val type: String) {
    object GetNotifications: Request("notifications")
    data class MakePost(val body: String): Request("post")
    data class MakeComment(val body: String, val postId: String): Request("comment")
    data class MakeLikePost(val postId: String): Request("like_post")
    data class MakeLikeComment(val commentId: String): Request("like_comment")
    data class MakeFollow(val userId: String): Request("follow")
    data class GetPost(val postId: String): Request("get_post")
    data class SearchAccounts(val query: String): Request("search_accounts")
    data class GetAccount(val userId: String): Request("get_account")

    companion object {
        fun Request.generateRequest(): String {
            return when(this) {
                is GetAccount -> "${this.type}&${this.userId}"
                is GetPost -> "${this.type}&${this.postId}"
                is MakeComment -> "${this.type}&${this.postId};${this.body}"
                is MakeFollow -> "${this.type}&${this.userId}"
                is MakeLikeComment -> "${this.type}&${this.commentId}"
                is MakeLikePost -> "${this.type}&${this.postId}"
                is MakePost -> "${this.type}&${this.body}"
                is SearchAccounts -> "${this.type}&${this.query}"
                GetNotifications -> this.type
            }
        }
    }
}
