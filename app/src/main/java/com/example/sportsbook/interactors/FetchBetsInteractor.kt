package com.example.sportsbook.interactors

import android.util.Log
import com.example.sportsbook.ApiService
import com.example.sportsbook.main.BetParser
import com.example.sportsbook.main.DailyBet
import com.example.sportsbook.persistence.BetsCache
import io.reactivex.Maybe
import org.joda.time.LocalDate
import javax.inject.Inject

class FetchBetsInteractor @Inject constructor(
    private val service: ApiService,
    private val parser: BetParser,
    private val cache: BetsCache
) {

    fun getBets(): Maybe<Map<LocalDate, List<DailyBet>>> {
        cache.clear()

        return service.getNcaaFootballLines()
            .flatMapMaybe { response ->
                Log.e("JIA", "got response")
                var result = response.body()
                    ?.let { parser.parse(it) }

                //remove all the bets that are not worth betting
                result?.forEach {
                    it.bets.apply {
                        removeAll { it.odds.toIntOrNull() == null }
                        removeAll { it.odds.toInt() > -1000 }
                    }
                }

                //remove all the days that has no bets
                result = result?.filter { it.bets.isNotEmpty() }

                result?.let {
                    if (it.isEmpty()) Maybe.empty()
                    else Maybe.just(it)
                } ?: Maybe.empty()
            }
            .map(::transformData)
            .flatMap {
                persistData(it)
                Maybe.just(it)
            }
    }

    private fun transformData(bets: List<DailyBet>) : Map<LocalDate, List<DailyBet>> {
        val dates = bets.map { it.date }.distinct().sorted()
        val map = mutableMapOf<LocalDate, List<DailyBet>>()
        dates.forEach { currentDate ->
            map[currentDate] = bets.filter { it.date == currentDate}
        }

        return map
    }

    private fun persistData(betsMap: Map<LocalDate, List<DailyBet>>) {
        betsMap.entries.forEach {
            Log.e("JIA", "Saving: ${it.key}::${it.value}")
            cache.save(it.key, it.value)
        }
    }

}