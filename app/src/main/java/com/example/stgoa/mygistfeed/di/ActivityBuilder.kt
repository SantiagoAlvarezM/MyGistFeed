package com.example.stgoa.mygistfeed.di

import com.example.stgoa.gistlib.di.scopes.ActivityScoped
import com.example.stgoa.mygistfeed.detail.DetailActivity
import com.example.stgoa.mygistfeed.home.HomeActivity
import com.example.stgoa.mygistfeed.home.HomeModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [HomeModule::class])
    internal abstract fun bindHomeActivity(): HomeActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [HomeModule::class])
    internal abstract fun bindDetailActivity(): DetailActivity
}