package com.example.sportsbook.interactors

import android.util.Log
import com.example.sportsbook.ApiService
import com.example.sportsbook.extensions.switchIfNull
import com.example.sportsbook.main.BetParser
import com.example.sportsbook.main.Category
import com.example.sportsbook.main.DailyBet
import com.example.sportsbook.persistence.BetsCache
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import org.joda.time.LocalDate
import retrofit2.Response
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

        val zipper = BiFunction<List<DailyBet>, List<DailyBet>, List<DailyBet>> { first, second ->
            Log.e(TAG, "zipping")
            first + second
        }

        return getNcaaf()
            .zipWith(getNfl(), zipper)
            .zipWith(getNcaaB(), zipper)
            .zipWith(getEsports(), zipper)
            .zipWith(getTableTennis(), zipper)
            .zipWith(getNba(), zipper)
            .zipWith(getNbaPreseason(), zipper)
            .zipWith(getUfc(), zipper)
            .zipWith(getBoxing(), zipper)
            .map(::transformData)
            .flatMap {
                persistData(it)
                Maybe.just(it)
            }
    }

    private fun getNcaaf() = service.getNcaaFootballLines().getData(Category.NCAAF)
    private fun getNfl() = service.getNflLines().getData(Category.NFL)
    private fun getNcaaB() = service.getNcaaBasketball().getData(Category.NCAAB)
    private fun getEsports() = service.getEsports().getData(Category.ESPORTS)
    private fun getTableTennis() = service.getTableTennis().getData(Category.TABLE_TENNIS)
    private fun getNba() = service.getNba().getData(Category.NBA)
    private fun getNbaPreseason() = service.getNbaPreseason().getData(Category.NBA_PRESEASON)
    private fun getUfc() = service.getUfc().getData(Category.UFC)
    private fun getBoxing() = service.getBoxing().getData(Category.BOXING)

    fun Single<Response<String>>.getData(category: Category): Maybe<List<DailyBet>> {
        return this
            .flatMapMaybe { response  ->
                response.body()
                    ?.let { parseAndFilterResponse(category, it) }
                    ?.let { Maybe.just(it) }
                    .switchIfNull { Maybe.just(emptyList()) }
            }
            .onErrorResumeNext (Maybe.just(emptyList()))
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