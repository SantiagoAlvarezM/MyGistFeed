package com.example.stgoa.gistlib.auth.modules

import android.content.SharedPreferences
import com.example.stgoa.gistlib.auth.login.model.UserProfile
import com.example.stgoa.gistlib.auth.services.AuthApiService
import com.example.stgoa.gistlib.auth.services.GithubConfig
import com.example.stgoa.gistlib.di.AuthRetrofitService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit

@Module
object AuthModule {

    @Provides
    @JvmStatic
    @Reusable
    fun provideAuthConfig(): GithubConfig = GithubConfig

    @Provides
    @JvmStatic
    @Reusable
    fun provideAuthService(@AuthRetrofitService retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @JvmStatic
    @Reusable
    fun provideUserProfile(preferences: SharedPreferences): UserProfile {
        return UserProfile(preferences)
    }
}