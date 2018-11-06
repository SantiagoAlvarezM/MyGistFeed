package com.example.stgoa.gistlib.di

import com.example.stgoa.gistlib.auth.login.LoginActivity
import com.example.stgoa.gistlib.auth.modules.AuthModule
import com.example.stgoa.gistlib.di.scopes.ActivityScoped
import com.example.stgoa.gistlib.gist.modules.GistApiModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [AuthModule::class, LibModule::class, GistApiModule::class])
abstract class LibActivityBuilder {

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun bindLoginActivity(): LoginActivity
}