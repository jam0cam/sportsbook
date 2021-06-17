package com.example.sportsbook.interactors

import android.util.Log
import com.example.sportsbook.network.ApiMap
import com.example.sportsbook.main.DailyBet
import com.example.sportsbook.network.ApiService
import com.example.sportsbook.persistence.BetsCache
import com.example.sportsbook.persistence.ErrorLogger
import io.reactivex.Maybe
import io.reactivex.functions.Function
import org.joda.time.LocalDate
import javax.inject.Inject

class FetchBetsInteractor @Inject constructor(
    private val cache: BetsCache,
    private val apiMap: ApiMap,
    private val service: ApiService,
    private val errorLogger: ErrorLogger,
) {

    companion object {
        val TAG = "FetchBetsInteractor"
    }

    fun getBets(): Maybe<Map<LocalDate, List<DailyBet>>> {
        Log.d(TAG, "getBets")
        cache.clear()
        errorLogger.clearLogs()

        val zipper = Function<Array<Any>, List<DailyBet>> {
            it.flatMap { it as List<DailyBet> }
        }

        return service.login()
            .flatMapMaybe {
                Log.e("JIA", "Hopefully successful login")
                Maybe.zip(apiMap.map1.values, zipper)
                    .map(::transformData)
                    .flatMap {
                        persistData(it)
                        Maybe.just(it)
                    }
            }
    }

    /**
     * Takes in a list of [DailyBet] and returns a map of [LocalDate] and [DailyBet]
     */
    private fun transformData(bets: List<DailyBet>): Map<LocalDate, List<DailyBet>> {
        val dates = bets.map { it.date }.distinct().sorted()
        val map = mutableMapOf<LocalDate, List<DailyBet>>()
        dates.forEach { currentDate ->
            map[currentDate] = bets.filter { it.date == currentDate }
        }

        return map
    }

    private fun persistData(betsMap: Map<LocalDate, List<DailyBet>>) {
        betsMap.entries.forEach {
            cache.save(it.key, it.value)
        }
    }

}