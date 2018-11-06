package com.example.stgoa.gistlib.common

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}

fun Disposable.disposeSecure() = this.let {
    if (it.isDisposed.not()) {
        it.dispose()
    }
}

fun <T> Flowable<T>.applySchedulers(): Flowable<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.applySchedulers(): Observable<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun Completable.applySchedulers(): Completable {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.applySchedulers(): Single<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Maybe<T>.applySchedulers(): Maybe<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.applySchedulersOnIo(): Flowable<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
}

fun <T> Observable<T>.applySchedulersOnIo(): Observable<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
}

fun Completable.applySchedulersOnIo(): Completable {
    return subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
}

fun <T> Single<T>.applySchedulersOnIo(): Single<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
}