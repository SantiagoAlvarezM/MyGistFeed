package com.example.stgoa.mygistfeed

import com.example.stgoa.gistlib.auth.modules.AuthModule
import com.example.stgoa.gistlib.di.LibModule
import com.example.stgoa.mygistfeed.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
            .authModule(AuthModule)
            .gistModule(LibModule)
            .build()
    }
}