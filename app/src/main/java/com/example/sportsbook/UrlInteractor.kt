package com.example.sportsbook

import android.util.Log
import com.example.sportsbook.main.BetParser
import com.example.sportsbook.main.DailyBet
import io.reactivex.Maybe
import javax.inject.Inject

class UrlInteractor @Inject constructor(
    private val service: ApiService,
    private val parser: BetParser
) {

    fun getBets(): Maybe<List<DailyBet>> {
        return service.getNcaaFootballLines()
            .flatMapMaybe { response ->
                Log.e("JIA", "got response")
                var result = response.body()
                    ?.let { parser.parse(it) }

                result?.forEach {
                    it.bets.apply {
                        removeAll { it.odds.toIntOrNull() == null }
                        removeAll { it.odds.toInt() > -1000 }
                    }
                }

                result?.let {
                    if (it.isEmpty()) Maybe.empty()
                    else Maybe.just(it)
                } ?: Maybe.empty()
            }
    }

}