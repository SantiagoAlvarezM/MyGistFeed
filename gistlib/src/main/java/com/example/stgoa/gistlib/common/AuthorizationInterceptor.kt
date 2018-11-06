package com.example.stgoa.gistlib.common

import android.content.SharedPreferences
import com.example.stgoa.gistlib.auth.services.GithubConfig.AUTHORIZATION
import com.example.stgoa.gistlib.auth.services.GithubConfig.GITHUB_API_HEADERS
import com.example.stgoa.gistlib.auth.services.GithubConfig.KEY_BASIC_TOKEN
import com.example.stgoa.gistlib.auth.services.GithubConfig.KEY_OAUTH_TOKEN
import com.example.stgoa.gistlib.auth.services.GithubConfig.KEY_PASSWORD
import com.example.stgoa.gistlib.auth.services.GithubConfig.KEY_USERNAME
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(private val preferences: SharedPreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = when {
            preferences.contains(KEY_OAUTH_TOKEN) -> preferences.getString(KEY_OAUTH_TOKEN, "")
            preferences.contains(KEY_BASIC_TOKEN) -> preferences.getString(KEY_BASIC_TOKEN, "")
            preferences.contains(KEY_USERNAME) && preferences.contains(KEY_PASSWORD) -> {
                Credentials.basic(
                    preferences.getString(KEY_USERNAME, ""), preferences.getString(KEY_PASSWORD, "")
                )
            }
            else -> ""
        }
        val requestBuilder = chain.request().newBuilder()
        if (accessToken.isNotEmpty()) requestBuilder.addHeader(AUTHORIZATION, accessToken)
        GITHUB_API_HEADERS.map { requestBuilder.addHeader(it.key, it.value) }
        return chain.proceed(requestBuilder.build())
    }
}
