package com.fylora.auth.data

interface AuthRepository {
    suspend fun signUp(username: String, password: String): AuthResult<String>
    suspend fun signIn(username: String, password: String): AuthResult<String>
    suspend fun authenticate(): AuthResult<String>
    suspend fun logout()
}