package com.example.sportsbook.interactors

import com.example.sportsbook.extensions.switchIfNull
import com.example.sportsbook.main.bets.BetUiModel
import com.example.sportsbook.persistence.BetsCache
import org.joda.time.LocalDate
import javax.inject.Inject

class GetBetsFromCacheInteractor @Inject constructor(
    private val cache: BetsCache
) {

    fun getBets(date : LocalDate) : List<BetUiModel>{
        return cache.get(date)
            ?.flatMap {
                it.bets.map { bet->
                    BetUiModel(it.date, it.category, bet.name, bet.odds)
                }
            }
            ?.sortedBy { it.category }
            .switchIfNull { emptyList() }
    }
}