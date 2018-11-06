package com.example.stgoa.gistlib.auth.services

import com.example.stgoa.gistlib.auth.services.model.AuthorizationRequest
import com.example.stgoa.gistlib.auth.services.model.AuthorizationResponse
import com.example.stgoa.gistlib.auth.services.model.UserResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface AuthApiService {

    @POST("authorizations")
    fun getAuthorization(@Body authRequestModel: AuthorizationRequest): Single<Response<AuthorizationResponse>>

    @DELETE("authorizations/{authorization_id}")
    fun deleteAuthorization(@Path("authorization_id") authorizationId: Int): Completable

    @GET("user")
    fun getUser(): Single<Response<UserResponse>>
}