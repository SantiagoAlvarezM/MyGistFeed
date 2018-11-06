package com.example.stgoa.gistlib.gist.controllers

import com.example.stgoa.gistlib.common.Resource
import com.example.stgoa.gistlib.gist.service.model.GistResponse
import io.reactivex.Single

interface GistController {

    fun getPublicGists(since: String = ""): Single<Resource<*>>

    fun getUserGists(since: String = ""): Single<Resource<*>>

    fun getStarredGists(since: String = ""): Single<Resource<*>>

    fun getGist(gistId: String): Single<GistResponse>
}