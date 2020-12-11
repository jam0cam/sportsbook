package com.example.sportsbook

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET("/sbk/sportsbook4/ncaa-football-betting/game-lines.sbk")
    fun getNcaaFootballLines(): Single<Response<String>>

    @GET("/sbk/sportsbook4/nfl-betting/nfl-game-lines.sbk")
    fun getNflLines(): Single<Response<String>>

    @GET("/sbk/sportsbook4/ncaa-basketball-betting/game-lines.sbk")
    fun getNcaaBasketball(): Single<Response<String>>
}