package com.example.stgoa.mygistfeed.base

import androidx.collection.ArrayMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stgoa.mygistfeed.di.ViewModelSubComponent
import com.example.stgoa.mygistfeed.home.GistsViewModel
import java.util.concurrent.Callable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Factory in charge of provide [ViewModel] instances to consumers
 *
 * [Source](https://proandroiddev.com/mvvm-architecture-viewmodel-and-livedata-part-2-di-1a6b1f96d84b)
 */
@Singleton
class ViewModelFactory @Inject
constructor(viewModelSubComponent: ViewModelSubComponent) : ViewModelProvider.Factory {

    private val creators: ArrayMap<Class<*>, Callable<out ViewModel>> = ArrayMap()

    init {
        creators[GistsViewModel::class.java] = Callable { viewModelSubComponent.gistsViewModel() }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Callable<out ViewModel>? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("Unknown model class $modelClass")
        }
        try {
            return creator.call() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
