package com.fylora.auth.di

import android.content.SharedPreferences
import com.fylora.auth.data.AuthApi
import com.fylora.auth.data.AuthRepository
import com.fylora.auth.data.AuthRepositoryImpl
import com.fylora.auth.domain.use_case.ValidatePasswordUseCase
import com.fylora.auth.domain.use_case.ValidateUsernameUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
@InstallIn(ViewModelComponent::class)
object AuthModule {
    @Provides
    @ViewModelScoped
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl("http://10.100.102.77:8080/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @ViewModelScoped
    fun provideAuthRepository(authApi: AuthApi, preferences: SharedPreferences): AuthRepository {
        return AuthRepositoryImpl(authApi, preferences)
    }

    @Provides
    @ViewModelScoped
    fun provideValidatePasswordUseCase(): ValidatePasswordUseCase {
        return ValidatePasswordUseCase()
    }

    @Provides
    @ViewModelScoped
    fun provideValidateUsernameUseCase(): ValidateUsernameUseCase {
        return ValidateUsernameUseCase()
    }
}
