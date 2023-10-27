package com.fylora.auth.data

import android.content.SharedPreferences
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.HttpException

class AuthRepositoryImplTest {

    private lateinit var authApi: AuthApi
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var authRepository: AuthRepositoryImpl

    @BeforeEach
    fun setUp() {
        authApi = mockk()
        sharedPreferences = mockk(relaxed = true)
        authRepository = AuthRepositoryImpl(authApi, sharedPreferences)
    }

    @Test
    fun `Test signup failed, returns error`() = runBlocking {
        coEvery { authApi.signUp(any()) } throws mockk<HttpException> {
            every { code() } returns 409
            every { message() } returns "The username is already taken"
            every { response()?.errorBody()?.string() } returns "The username is already taken"
            every { printStackTrace() } returns Unit
        }

        val result = authRepository.signUp(
            username = "Test",
            password = "Test"
        )

        assertThat(result.data).isEqualTo(
                "The username is already taken"
        )
        assertThat(result is AuthResult.UnknownError).isTrue()
    }

    @Test
    fun `Test login failed, returns error`() = runBlocking {
        coEvery { authApi.signIn(any()) } throws mockk<HttpException> {
            every { code() } returns 409
            every { message() } returns "Incorrect username or password"
            every { response()?.errorBody()?.string() } returns "Incorrect username or password"
            every { printStackTrace() } returns Unit
        }

        val result = authRepository.signIn(
            username = "Test",
            password = "Test"
        )

        assertThat(result.data).isEqualTo(
            "Incorrect username or password"
        )
        assertThat(result is AuthResult.UnknownError).isTrue()
    }

    @Test
    fun `Test login successful, returns Authorized`() = runBlocking {
        coEvery { authApi.signIn(any()) } returns TokenResponse("")

        val result = authRepository.signIn(
            username = "Test",
            password = "Test"
        )

        assertThat(result is AuthResult.Authorized).isTrue()
    }
}