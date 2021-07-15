package com.example.sportsbook.network

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("/cca/customerauthn/pl/login")
    fun login(
        @Field("login_fail") loginFail: String = "/ctr/acctmgt/pl/rui/login.ctr?service=/sbk/sportsbook4/home.sbk",
        @Field("service") service: String = "/sbk/sportsbook4/home.sbk",
        @Field("username") description: String = "jam0cam1",
        @Field("password") password: String = "jam0cam2",
    ): Single<Response<String>>

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

    @GET("/sbk/sportsbook4/tennis-betting/game-lines.sbk")
    fun getTennis(): Single<Response<String>>

    @GET("/sbk/sportsbook4/tennis-betting/wimbledon-matches.sbk")
    fun getWimbledon(): Single<Response<String>>

    @GET("/sbk/sportsbook4/soccer-betting/today's-games-all-today's-games.sbk")
    fun getSoccer(): Single<Response<String>>
}