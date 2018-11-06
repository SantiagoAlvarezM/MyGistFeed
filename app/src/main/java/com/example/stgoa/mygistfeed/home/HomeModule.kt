package com.example.stgoa.mygistfeed.home

import com.example.stgoa.gistlib.di.scopes.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindGistsFragment(): GistsFragment
}