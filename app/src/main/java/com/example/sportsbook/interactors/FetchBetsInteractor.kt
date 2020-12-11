package com.example.sportsbook.interactors

import android.graphics.Bitmap
import android.util.Log
import com.example.sportsbook.ApiService
import com.example.sportsbook.extensions.switchIfNull
import com.example.sportsbook.main.BetParser
import com.example.sportsbook.main.Category
import com.example.sportsbook.main.DailyBet
import com.example.sportsbook.persistence.BetsCache
import com.example.sportsbook.toMaybe
import io.reactivex.Maybe
import io.reactivex.functions.BiFunction
import org.joda.time.LocalDate
import javax.inject.Inject

class FetchBetsInteractor @Inject constructor(
    private val service: ApiService,
    private val parser: BetParser,
    private val cache: BetsCache
) {

    companion object {
        val TAG = "FetchBetsInteractor"
    }

    fun getBets(): Maybe<Map<LocalDate, List<DailyBet>>> {
        Log.e(TAG, "getBets")
        cache.clear()

        val ncaaf = getNcaaf()
        val nfl = getNfl()
        val ncaab = getNcaaB()
        val zipper = BiFunction<List<DailyBet>, List<DailyBet>, List<DailyBet>> { first, second ->
            Log.e(TAG, "zipping")
            first + second
        }

        return ncaaf
            .zipWith(nfl, zipper)
            .zipWith(ncaab, zipper)
            .map(::transformData)
            .flatMap {
                persistData(it)
                Maybe.just(it)
            }
    }

    private fun getNcaaf(): Maybe<List<DailyBet>> {
        return service.getNcaaFootballLines()
            .flatMapMaybe { response ->
                response.body()
                    ?.let { parseAndFilterResponse(Category.NCAAF, it) }
                    ?.let { Maybe.just(it) }
                    .switchIfNull { Maybe.just(emptyList()) }
            }
            .doOnComplete { Log.e(TAG, "NCAAF doOnComplete") }
            .doOnSuccess { Log.e(TAG, "NCAAF doOnSuccess") }
            .doOnError {
                Log.e(TAG, "${it.message}")
            }
    }

    private fun getNfl(): Maybe<List<DailyBet>> {
        return service.getNflLines()
            .flatMapMaybe { response ->
                response.body()
                    ?.let { parseAndFilterResponse(Category.NFL, it) }
                    ?.let { Maybe.just(it) }
                    .switchIfNull { Maybe.just(emptyList()) }
            }
            .doOnComplete { Log.e(TAG, "NFL doOnComplete") }
            .doOnSuccess { Log.e(TAG, "NFL doOnSuccess") }
            .doOnError {
                Log.e(TAG, "${it.message}")
            }
    }

    private fun getNcaaB(): Maybe<List<DailyBet>> {
        return service.getNcaaBasketball()
            .flatMapMaybe { response ->
                response.body()
                    ?.let { parseAndFilterResponse(Category.NCAAB, it) }
                    ?.let { Maybe.just(it) }
                    .switchIfNull { Maybe.just(emptyList()) }
            }
            .doOnComplete { Log.e(TAG, "getNcaaB doOnComplete") }
            .doOnSuccess { Log.e(TAG, "getNcaaB doOnSuccess") }
            .doOnError {
                Log.e(TAG, "${it.message}")
            }
    }


    private fun parseAndFilterResponse(category: Category, response: String): List<DailyBet> {
        var result = parser.parse(category, response)

        //remove all the bets that are not worth betting
        result.forEach {
            it.bets.apply {
                removeAll { it.odds.toIntOrNull() == null }
                removeAll { it.odds.toInt() > -1000 }
            }
        }

        //remove all the days that has no bets
        return result.filter { it.bets.isNotEmpty() }
    }


    private fun transformData(bets: List<DailyBet>): Map<LocalDate, List<DailyBet>> {
        Log.e(TAG, "transformData")
        val dates = bets.map { it.date }.distinct().sorted()
        val map = mutableMapOf<LocalDate, List<DailyBet>>()
        dates.forEach { currentDate ->
            map[currentDate] = bets.filter { it.date == currentDate }
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