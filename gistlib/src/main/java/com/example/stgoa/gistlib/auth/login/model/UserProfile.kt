package com.example.stgoa.gistlib.auth.login.model

import android.content.SharedPreferences
import com.example.stgoa.gistlib.auth.services.GithubConfig.KEY_AUTHORIZATION_RESPONSE
import com.example.stgoa.gistlib.auth.services.GithubConfig.KEY_USER_RESPONSE
import com.example.stgoa.gistlib.auth.services.model.AuthorizationResponse
import com.example.stgoa.gistlib.auth.services.model.UserResponse
import com.example.stgoa.gistlib.di.scopes.ActivityScoped
import com.google.gson.Gson
import javax.inject.Inject

//TODO refactor this class, create a user repository
class UserProfile @Inject constructor(private val preferences: SharedPreferences) {

    val authenticated: Boolean = getUserData() != null
    val avatarUrl: String = getUserData()?.avatarUrl.orEmpty()
    val username: String = getUserData()?.login.orEmpty()
    val authorizationId: Int = getAuthData()?.id ?: 0

    fun clear() {
        preferences.edit().clear().apply()
    }

    private fun getAuthData(): AuthorizationResponse? {
        val json = preferences.getString(KEY_AUTHORIZATION_RESPONSE, "").orEmpty()
        return if (json.isNotEmpty()) Gson().fromJson(json, AuthorizationResponse::class.java) else null
    }

    private fun getUserData(): UserResponse? {
        val json = preferences.getString(KEY_USER_RESPONSE, "").orEmpty()
        return if (json.isNotEmpty()) Gson().fromJson(json, UserResponse::class.java) else null
    }
}