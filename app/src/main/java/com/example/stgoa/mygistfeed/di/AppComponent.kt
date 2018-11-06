package com.example.stgoa.mygistfeed.di

import android.app.Application
import com.example.stgoa.gistlib.auth.modules.AuthModule
import com.example.stgoa.gistlib.di.LibActivityBuilder
import com.example.stgoa.gistlib.di.LibModule
import com.example.stgoa.mygistfeed.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityBuilder::class, LibActivityBuilder::class])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder

        fun authModule(authModule: AuthModule): Builder

        fun gistModule(gistModule: LibModule): Builder
    }
}