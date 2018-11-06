package com.example.stgoa.gistlib.gist.service

import com.example.stgoa.gistlib.gist.service.model.GistResponse
import com.example.stgoa.gistlib.gist.service.model.GistsResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GistApiService {

    @GET("gists/public")
    fun getPublicGists(@Query("since") since: String): Single<Response<List<GistsResponse>>>

    @GET("users/{username}/gists")
    fun getUserGists(@Path("username") username: String, @Query("since") since: String): Single<Response<List<GistsResponse>>>

    @GET("gists/starred")
    fun getStarredGists(@Query("since") since: String): Single<Response<List<GistsResponse>>>

    @GET("gists/{gist_id}")
    fun getGist(@Path("gist_id") gistId: String): Single<GistResponse>
}