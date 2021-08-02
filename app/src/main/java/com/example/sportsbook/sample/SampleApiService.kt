package com.example.sportsbook.sample

import io.reactivex.Completable
import retrofit2.http.Body
import retrofit2.http.POST

interface SampleApiService {

    @POST("eventslogging/v1/eventset")
    fun dispatchHydraEvents(@Body eventSet: HydraEventSet): Completable
}