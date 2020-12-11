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

    @GET("/sbk/sportsbook4/esports-betting/esports-matchups.sbk")
    fun getEsports(): Single<Response<String>>

    @GET("/sbk/sportsbook4/table-tennis-betting/table-tennis.sbk")
    fun getTableTennis(): Single<Response<String>>

    @GET("/sbk/sportsbook4/nba-betting/nba-pre-season-lines-nba-pre-season-lines.sbk")
    fun getNbaPreseason(): Single<Response<String>>

    @GET("/sbk/sportsbook4/nba-betting/nba-game-lines.sbk")
    fun getNba(): Single<Response<String>>

    @GET("/sbk/sportsbook4/ufc-mma-betting/ufc-matchups.sbk")
    fun getUfc(): Single<Response<String>>

    @GET("/sbk/sportsbook4/boxing-betting/boxing-fight-odds.sbk")
    fun getBoxing(): Single<Response<String>>
}