package com.example.sportsbook

import android.util.Log
import com.example.sportsbook.extensions.switchIfNull
import com.example.sportsbook.interactors.FetchBetsInteractor
import com.example.sportsbook.main.BetParser
import com.example.sportsbook.main.Category
import com.example.sportsbook.main.Category.*
import com.example.sportsbook.main.DailyBet
import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class ApiMap @Inject constructor(
    service: ApiService,
    private val parser: BetParser
) {
    val map1 = mapOf(
        NCAAF to service.getNcaaFootballLines().getData(NCAAF),
        NFL to service.getNflLines().getData(NFL),
        NCAAB to service.getNcaaBasketball().getData(NCAAB),
        ESPORTS to service.getEsports().getData(ESPORTS),
        TABLE_TENNIS to service.getTableTennis().getData(TABLE_TENNIS),
        NBA_PRESEASON to service.getNbaPreseason().getData(NBA_PRESEASON),
        NBA to service.getNba().getData(NBA),
        UFC to service.getUfc().getData(UFC),
        BOXING to service.getBoxing().getData(BOXING),
        TENNIS to service.getTennis().getData(TENNIS),
        SOCCER to service.getSoccer().getData(SOCCER),
    )

    /**
     * Takes the HTML api response and converts it into a list of [DailyBet]
     */
    private fun Single<Response<String>>.getData(category: Category): Maybe<List<DailyBet>> {
        return this
            .flatMapMaybe { response  ->
                Log.d(FetchBetsInteractor.TAG, "Response received for ${category.value}")
                response.body()
                    ?.let { parseAndFilterResponse(category, it) }
                    ?.let { Maybe.just(it) }
                    .switchIfNull { Maybe.just(emptyList()) }
            }
            .onErrorResumeNext (Maybe.just(emptyList()))
            .doOnError {
                Log.d(FetchBetsInteractor.TAG, "${it.message}")
            }
    }

    /**
     * parses HTML to [DailyBet] and filters out bets we don't care about.
     */
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

}