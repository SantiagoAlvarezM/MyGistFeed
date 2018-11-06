package com.example.stgoa.gistlib.gist.controllers

import com.example.stgoa.gistlib.auth.login.model.UserProfile
import com.example.stgoa.gistlib.common.Resource
import com.example.stgoa.gistlib.common.applySchedulers
import com.example.stgoa.gistlib.gist.service.GistApiService
import com.example.stgoa.gistlib.gist.service.model.ApiError
import com.example.stgoa.gistlib.gist.service.model.GistResponse
import com.example.stgoa.gistlib.gist.service.model.GistsResponse
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

//TODO store in a DB, use pagination, LiveData
class GistControllerImpl @Inject constructor(
    private val service: GistApiService,
    private val userProfile: UserProfile
) : GistController {

    override fun getPublicGists(since: String): Single<Resource<*>> {
        return service.getPublicGists(since)
            .map(this::mapResponse)
            .applySchedulers()
    }

    override fun getUserGists(since: String): Single<Resource<*>> {
        return service.getUserGists(userProfile.username, since)
            .map(this::mapResponse)
            .applySchedulers()
    }

    override fun getStarredGists(since: String): Single<Resource<*>> {
        return service.getStarredGists(since)
            .map(this::mapResponse)
            .applySchedulers()
    }

    private fun mapResponse(response: Response<List<GistsResponse>>): Resource<*> {
        return if (response.isSuccessful) {
            Resource.success(response.body())
        } else {
            Resource.error(response.message(), ApiError(response.code()))
        }
    }

    override fun getGist(gistId: String): Single<GistResponse> {
        return service.getGist(gistId)
            .applySchedulers()
    }
}