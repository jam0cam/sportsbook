package com.example.sportsbook

import javax.inject.Inject

class UrlInteractor @Inject constructor(
    private val service: ApiService
){

    fun getBets(): List<Bet> {
        // TODO: JIA:
        return listOf()
    }

}