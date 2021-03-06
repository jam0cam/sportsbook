package com.example.sportsbook

import io.reactivex.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


inline fun <reified T> Observable<T>.withSchedulers(schedulers: MySchedulers): Observable<T> {
    return this
        .subscribeOn(schedulers.execution())
        .observeOn(schedulers.main())
}

inline fun <reified T> Maybe<T>.withSchedulers(schedulers: MySchedulers): Maybe<T> {
    return this
        .subscribeOn(schedulers.execution())
        .observeOn(schedulers.main())
}

inline fun <reified T> Flowable<T>.withSchedulers(schedulers: MySchedulers): Flowable<T> {
    return this
        .subscribeOn(schedulers.execution())
        .observeOn(schedulers.main())
}

inline fun <reified T> Single<T>.withSchedulers(schedulers: MySchedulers): Single<T> {
    return this
        .subscribeOn(schedulers.execution())
        .observeOn(schedulers.main())
}

fun Completable.withSchedulers(schedulers: MySchedulers): Completable {
    return this
        .subscribeOn(schedulers.execution())
        .observeOn(schedulers.main())
}

fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

inline fun <T> List<T>?.toMaybe(): Maybe<List<T>> {
    return if (this.isNullOrEmpty()) Maybe.empty()
    else Maybe.just(this)
}
