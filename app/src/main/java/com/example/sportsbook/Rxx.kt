package com.example.sportsbook

import io.reactivex.*


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
