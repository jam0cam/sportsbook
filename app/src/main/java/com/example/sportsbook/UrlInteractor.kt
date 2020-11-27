package com.example.sportsbook

import android.util.Log
import io.reactivex.Maybe
import javax.inject.Inject

class UrlInteractor @Inject constructor(
    private val service: ApiService,
    private val parser: BetParser
) {

    fun getBets(): Maybe<List<Bet>> {
        return service.getNcaaFootballLines()
            .flatMapMaybe { response ->
                Log.e("JIA", "got response")
                var result = response.body()
                    ?.let { parser.parse(it) }
                    ?.filter { it.odds.toIntOrNull() != null }
                    ?.filter { it.odds.toInt() < -1000 }

                result?.let { Maybe.just(it) } ?: Maybe.empty()
            }
    }

}