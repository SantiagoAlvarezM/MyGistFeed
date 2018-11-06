package com.example.stgoa.gistlib.auth.controllers

import com.example.stgoa.gistlib.auth.services.model.AuthorizationResponse
import com.example.stgoa.gistlib.auth.services.model.UserResponse
import com.example.stgoa.gistlib.common.Resource
import io.reactivex.Completable
import io.reactivex.Single

interface LoginController {

    fun getAuthorization(username: String, password: String): Single<Resource<AuthorizationResponse>>

    fun deleteAuthorization(authorizationId: Int): Completable

    fun getUser(): Single<Resource<UserResponse>>
}