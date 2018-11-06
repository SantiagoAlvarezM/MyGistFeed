package com.example.stgoa.mygistfeed.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.stgoa.mygistfeed.base.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger [Module] responsible of make Application-wide components injectable
 *
 * @author santiagoalvarez
 */
@Module(subcomponents = [(ViewModelSubComponent::class)])
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideViewModelFactory(viewModelSubComponent: ViewModelSubComponent.Builder): ViewModelProvider.Factory =
        ViewModelFactory(viewModelSubComponent.build())
}