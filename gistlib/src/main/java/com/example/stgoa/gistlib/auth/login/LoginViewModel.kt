package com.example.stgoa.gistlib.auth.login

import com.example.stgoa.gistlib.auth.controllers.LoginControllerImpl
import com.example.stgoa.gistlib.auth.login.model.LoginUIModel
import com.example.stgoa.gistlib.common.applySchedulers
import com.example.stgoa.gistlib.di.scopes.ActivityScoped
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@ActivityScoped
class LoginViewModel @Inject constructor(private val controller: LoginControllerImpl) {

    private val manageViewSubject = PublishSubject.create<LoginUIModel>()
    val disposable = CompositeDisposable()

    fun doBasicLogin(username: String, password: String) {
        disposable.add(
            controller.getAuthorization(username, password)
                .concatWith { controller.getUser() }
                .doAfterTerminate { manageViewSubject.onNext(LoginUIModel.ShowProgress(false)) }
                .subscribe({ manageViewSubject.onNext(LoginUIModel.LoginSuccess) }
                    , { manageViewSubject.onNext(LoginUIModel.LoginFails(it)) })
        )
    }

    fun observableManageView(): Observable<LoginUIModel> = manageViewSubject.applySchedulers()
}