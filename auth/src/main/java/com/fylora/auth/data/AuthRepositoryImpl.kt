package com.fylora.auth.data

import android.content.SharedPreferences
import androidx.core.content.edit
import retrofit2.HttpException
import java.net.SocketTimeoutException

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
        } catch (e: SocketTimeoutException) {
            AuthResult.UnknownError("Timeout, please check your internet connection and try again.")
        } catch (e: Exception) {
            e.printStackTrace()
            println("the error is unknown")
            AuthResult.UnknownError()
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
        } catch (e: SocketTimeoutException) {
            println("timeout error")
            AuthResult.UnknownError("Timeout, please check your internet connection and try again.")
        } catch (e: Exception) {
            println("Unknown error signing in")
            e.printStackTrace()
            AuthResult.UnknownError("Unknown error signing in")
        }
    }

    override suspend fun authenticate(): AuthResult<String> {
        return try {
            val token = prefs.getString("jwt", null)
                ?: return AuthResult.Unauthorized("You are not authorized")
            authApi.authenticate("Bearer $token")
            AuthResult.Authorized()
        } catch (e: HttpException) {
            e.printStackTrace()
            if(e.code() == HttpCodes.UNAUTHORIZED.code){
                AuthResult.Unauthorized(e.response()?.errorBody()?.string())
            } else {
                AuthResult.UnknownError(e.response()?.errorBody()?.string())
            }
        } catch (e: SocketTimeoutException) {
            e.printStackTrace()
            AuthResult.UnknownError("Timeout, please check your internet connection and try again.")
        } catch (e: Exception) {
            e.printStackTrace()
            AuthResult.UnknownError("You are not authorized")
        }
    }

    override suspend fun logout() {
        prefs.edit {
            this.remove("jwt")
        }
    }
}