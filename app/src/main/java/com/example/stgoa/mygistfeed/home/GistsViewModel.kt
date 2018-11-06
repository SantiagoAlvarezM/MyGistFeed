package com.example.stgoa.mygistfeed.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.stgoa.gistlib.auth.login.model.UserProfile
import com.example.stgoa.gistlib.common.Resource
import com.example.stgoa.gistlib.common.Status
import com.example.stgoa.gistlib.common.applySchedulers
import com.example.stgoa.gistlib.common.disposeSecure
import com.example.stgoa.gistlib.gist.controllers.GistControllerImpl
import com.example.stgoa.gistlib.gist.service.model.GistsResponse
import com.example.stgoa.mygistfeed.common.toItem
import com.example.stgoa.mygistfeed.common.toListItem
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

//TODO map errors to show appropriate message/view
class GistsViewModel @Inject constructor(
    private val gistController: GistControllerImpl,
    private val userProfile: UserProfile
) : ViewModel() {

    private val viewModelActionsSubject = PublishSubject.create<Resource<*>>()
    private val disposable = CompositeDisposable()


    fun loadData(currentFeedType: FeedType) {
        val source = when (currentFeedType) {
            FeedType.PUBLIC -> gistController.getPublicGists()
            FeedType.USER -> gistController.getUserGists()
            FeedType.STARRED -> gistController.getStarredGists()
        }
        disposable.add(
            source
                .map { it ->
                    return@map if (it.status == Status.SUCCESS) {
                        Resource.success((it.data as List<GistsResponse>).map { it.toListItem() })
                    } else {
                        it
                    }
                }
                .doOnSubscribe { viewModelActionsSubject.onNext(Resource.loading(null)) }
                .subscribe({
                    viewModelActionsSubject.onNext(it)
                }, {
                    viewModelActionsSubject.onNext(Resource.error(it.message.orEmpty(), null))
                })
        )
    }

    fun loadGist(gistId: String) {
        disposable.add(
            gistController.getGist(gistId)
                .map { it.toItem() }
                .doOnSubscribe { viewModelActionsSubject.onNext(Resource.loading(null)) }
                .subscribe({ viewModelActionsSubject.onNext(Resource.success(it)) },
                    {
                        viewModelActionsSubject.onNext(Resource.error(it.message.orEmpty(), null))
                        Log.d("loadGist", it.message)
                    })
        )
    }

    fun getUser(): UserProfile {
        return userProfile
    }

    fun observableViewModelActions(): Observable<Resource<*>> {
        return viewModelActionsSubject.applySchedulers()
    }

    override fun onCleared() {
        disposable.disposeSecure()
        super.onCleared()
    }
}