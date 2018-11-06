package com.example.stgoa.gistlib.gist.modules

import com.example.stgoa.gistlib.di.AuthRetrofitService
import com.example.stgoa.gistlib.gist.service.GistApiService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit

@Module
object GistApiModule {

    @Provides
    @JvmStatic
    @Reusable
    fun gistApiService(@AuthRetrofitService retrofit: Retrofit): GistApiService {
        return retrofit.create(GistApiService::class.java)
    }
}