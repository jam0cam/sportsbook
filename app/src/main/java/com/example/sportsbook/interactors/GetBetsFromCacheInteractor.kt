package com.example.sportsbook.interactors

import com.example.sportsbook.main.Bet
import com.example.sportsbook.persistence.BetsCache
import org.joda.time.LocalDate
import javax.inject.Inject

class GetBetsFromCacheInteractor @Inject constructor(
    private val cache: BetsCache
) {

    fun getBets(date : LocalDate) : List<Bet>{
        return cache.get(date)
            ?.map { it.bets }
            ?.flatten()
            ?: emptyList()
    }
}