package com.example.stgoa.mygistfeed.di

import com.example.stgoa.mygistfeed.home.GistsViewModel
import dagger.Subcomponent

@Subcomponent
interface ViewModelSubComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelSubComponent
    }

    fun gistsViewModel(): GistsViewModel
}