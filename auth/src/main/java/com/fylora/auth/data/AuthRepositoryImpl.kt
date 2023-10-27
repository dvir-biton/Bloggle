package com.fylora.auth.data

import android.content.SharedPreferences
import androidx.core.content.edit
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val prefs: SharedPreferences
): AuthRepository {
    override suspend fun signUp(username: String, password: String): AuthResult<String> {
        return try {
            authApi.signUp(
                request = AuthRequest(
                    username = username,
                    password = password
                )
            )
            signIn(username, password)
        } catch (e: HttpException) {
            if(e.code() == HttpCodes.UNAUTHORIZED.code){
                AuthResult.Unauthorized(e.response()?.errorBody()?.string())
            } else {
                val error = e.response()?.errorBody()?.string()
                e.printStackTrace()
                println("the error is HTTP exception: $error")
                AuthResult.UnknownError(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println("the error is unknown")
            AuthResult.UnknownError(null)
        }
    }

    override suspend fun signIn(username: String, password: String): AuthResult<String> {
        return try {
            val response = authApi.signIn(
                request = AuthRequest(
                    username = username,
                    password = password
                )
            )
            prefs.edit()
                .putString("jwt", response.token)
                .apply()
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if(e.code() == HttpCodes.UNAUTHORIZED.code){
                AuthResult.Unauthorized(e.response()?.errorBody()?.string())
            } else {
                AuthResult.UnknownError(e.response()?.errorBody()?.string())
            }
        } catch (e: Exception) {
            println("Unknown error signing in")
            AuthResult.UnknownError("Unknown error signing in")
        }
    }

    override suspend fun authenticate(): AuthResult<String> {
        return try {
            val token = prefs.getString("jwt", null) ?: return AuthResult.Unauthorized("You are not authorized")
            authApi.authenticate("Bearer $token")
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if(e.code() == HttpCodes.UNAUTHORIZED.code){
                AuthResult.Unauthorized(e.response()?.errorBody()?.string())
            } else {
                AuthResult.UnknownError(e.response()?.errorBody()?.string())
            }
        } catch (e: Exception) {
            AuthResult.UnknownError("You are not authorized")
        }
    }

    override suspend fun logout() {
        prefs.edit {
            this.remove("jwt")
        }
    }
}