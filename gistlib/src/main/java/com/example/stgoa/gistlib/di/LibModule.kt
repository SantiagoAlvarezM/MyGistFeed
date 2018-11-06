package com.example.stgoa.gistlib.di

import android.content.Context
import android.content.SharedPreferences
import com.example.stgoa.gistlib.auth.services.GithubConfig
import com.example.stgoa.gistlib.common.AuthorizationInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object LibModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("App", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    @AuthRetrofitService
    fun provideOkhttpClient(preferences: SharedPreferences): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.MINUTES)
            .connectTimeout(5, TimeUnit.MINUTES)
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .addInterceptor(AuthorizationInterceptor(preferences))
            .build()
    }

    @Provides
    @Singleton
    @AuthRetrofitService
    fun provideRetrofit(@AuthRetrofitService httpClient: OkHttpClient, config: GithubConfig): Retrofit {
        return Retrofit.Builder()
            .baseUrl(config.GITHUB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
    }
}