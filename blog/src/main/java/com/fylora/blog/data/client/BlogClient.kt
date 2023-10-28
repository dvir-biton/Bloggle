package com.fylora.blog.data.client

import com.fylora.blog.data.model.Account
import com.fylora.blog.data.model.Comment
import com.fylora.blog.data.model.Notification
import com.fylora.blog.data.model.Post
import com.fylora.blog.data.model.Resource

interface BlogClient {
    suspend fun requestGetNotifications(request: Request.GetNotifications): Resource<List<Notification>>
    suspend fun requestMakePost(request: Request.MakePost): Resource<Post>
    suspend fun requestMakeComment(request: Request.MakeComment): Resource<Comment>
    suspend fun requestMakeLikePost(request: Request.MakeLikePost)
    suspend fun requestMakeLikeComment(request: Request.MakeLikeComment)
    suspend fun requestMakeFollow(request: Request.MakeFollow)
    suspend fun requestGetPost(request: Request.GetPost): Resource<List<Comment>>
    suspend fun requestSearchAccounts(request: Request.SearchAccounts): Resource<List<Account>>
    suspend fun requestGetAccount(request: Request.GetAccount): Resource<Account>
    suspend fun close()
}
