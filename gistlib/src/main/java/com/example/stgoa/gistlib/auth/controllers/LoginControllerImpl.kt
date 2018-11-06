package com.example.stgoa.gistlib.auth.controllers

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.stgoa.gistlib.auth.services.AuthApiService
import com.example.stgoa.gistlib.auth.services.GithubConfig.GITHUB_API_SCOPES
import com.example.stgoa.gistlib.auth.services.GithubConfig.GITHUB_CLIENT_ID
import com.example.stgoa.gistlib.auth.services.GithubConfig.GITHUB_CLIENT_SECRET
import com.example.stgoa.gistlib.auth.services.GithubConfig.GITHUB_REQUEST_NOTE
import com.example.stgoa.gistlib.auth.services.GithubConfig.KEY_AUTHORIZATION_RESPONSE
import com.example.stgoa.gistlib.auth.services.GithubConfig.KEY_BASIC_TOKEN
import com.example.stgoa.gistlib.auth.services.GithubConfig.KEY_USER_RESPONSE
import com.example.stgoa.gistlib.auth.services.model.AuthorizationRequest
import com.example.stgoa.gistlib.auth.services.model.AuthorizationResponse
import com.example.stgoa.gistlib.auth.services.model.UserResponse
import com.example.stgoa.gistlib.common.Resource
import com.example.stgoa.gistlib.common.Status
import com.example.stgoa.gistlib.common.applySchedulers
import com.example.stgoa.gistlib.di.scopes.ActivityScoped
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.Credentials
import retrofit2.Response
import javax.inject.Inject

//TODO clean up this, use AccountManager or a DB  to store credentials
@ActivityScoped
class LoginControllerImpl @Inject constructor(
    private val service: AuthApiService,
    private val preferences: SharedPreferences
) : LoginController {

    override fun getAuthorization(username: String, password: String): Single<Resource<AuthorizationResponse>> {
        val token = Credentials.basic(username, password)
        preferences.edit { putString(KEY_BASIC_TOKEN, token) }
        return service.getAuthorization(
            AuthorizationRequest(GITHUB_REQUEST_NOTE, GITHUB_API_SCOPES, GITHUB_CLIENT_ID, GITHUB_CLIENT_SECRET)
        ).flatMap { if (it.isSuccessful) Single.just(it) else Single.error(IllegalStateException(it.message())) }
            .map(this::mapAuthorizationResponse)
            .doOnSuccess(this::saveResponse)
            .applySchedulers()
    }

    private fun mapAuthorizationResponse(response: Response<AuthorizationResponse>): Resource<AuthorizationResponse> {
        return if (response.isSuccessful) {
            Resource.success(response.body())
        } else {
            Resource.error(response.message(), null)
        }
    }

    private fun saveResponse(resource: Resource<AuthorizationResponse>) {
        if (resource.status == Status.SUCCESS) {
            preferences.edit { putString(KEY_AUTHORIZATION_RESPONSE, Gson().toJson(resource.data)) }
        }
    }

    override fun deleteAuthorization(authorizationId: Int): Completable {
        return service.deleteAuthorization(authorizationId)
    }

    override fun getUser(): Single<Resource<UserResponse>> {
        return service.getUser()
            .flatMap { if (it.isSuccessful) Single.just(it) else Single.error(IllegalStateException(it.message())) }
            .map(this::mapUserResponse)
            .doOnSuccess(this::saveUserResponse)
            .applySchedulers()
    }

    private fun mapUserResponse(response: Response<UserResponse>): Resource<UserResponse> {
        return if (response.isSuccessful) {
            Resource.success(response.body())
        } else {
            Resource.error(response.message(), null)
        }
    }

    private fun saveUserResponse(resource: Resource<UserResponse>) {
        if (resource.status == Status.SUCCESS) {
            preferences.edit { putString(KEY_USER_RESPONSE, Gson().toJson(resource.data)) }
        }
    }
}