package com.example.sportsbook

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET("/sbk/sportsbook4/ncaa-football-betting/game-lines.sbk")
    fun getNcaaLines(): Single<Response<String>>
}