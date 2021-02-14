package com.example.sportsbook

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface ApiService {
    @GET("/sbk/sportsbook4/ncaa-football-betting/game-lines.sbk")
    fun getNcaaFootballLines(@Header("Cookie") cookie: String): Single<Response<String>>

    @GET("/sbk/sportsbook4/nfl-betting/nfl-game-lines.sbk")
    fun getNflLines(@Header("Cookie") cookie: String): Single<Response<String>>

    @GET("/sbk/sportsbook4/ncaa-basketball-betting/game-lines.sbk")
    fun getNcaaBasketball(@Header("Cookie") cookie: String): Single<Response<String>>

    @GET("/sbk/sportsbook4/esports-betting/esports-matchups.sbk")
    fun getEsports(@Header("Cookie") cookie: String): Single<Response<String>>

    @GET("/sbk/sportsbook4/table-tennis-betting/table-tennis.sbk")
    fun getTableTennis(@Header("Cookie") cookie: String): Single<Response<String>>

    @GET("/sbk/sportsbook4/nba-betting/nba-pre-season-lines-nba-pre-season-lines.sbk")
    fun getNbaPreseason(@Header("Cookie") cookie: String): Single<Response<String>>

    @GET("/sbk/sportsbook4/nba-betting/nba-game-lines.sbk")
    fun getNba(@Header("Cookie") cookie: String): Single<Response<String>>

    @GET("/sbk/sportsbook4/ufc-mma-betting/ufc-matchups.sbk")
    fun getUfc(@Header("Cookie") cookie: String): Single<Response<String>>

    @GET("/sbk/sportsbook4/boxing-betting/boxing-fight-odds.sbk")
    fun getBoxing(@Header("Cookie") cookie: String): Single<Response<String>>

    @GET("/sbk/sportsbook4/tennis-betting/all-tennis-game-lines.sbk")
    fun getTennis(@Header("Cookie") cookie: String): Single<Response<String>>

    @GET("/sbk/sportsbook4/soccer-betting/today's-games-all-today's-games.sbk")
    fun getSoccer(@Header("Cookie") cookie: String): Single<Response<String>>
}