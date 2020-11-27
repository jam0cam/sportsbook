package com.example.sportsbook

import android.util.Log
import io.reactivex.Maybe
import javax.inject.Inject

class UrlInteractor @Inject constructor(
    private val service: ApiService,
    private val schedulers: MySchedulers
) {

    fun getBets(): Maybe<List<Bet>> {
        service.getNcaaLines()
            .map {
                Log.e("JIA", "response: ${it.body()}")
            }.withSchedulers(schedulers)
            .subscribe({
                Log.e("JIA", "success")
            }, {
                Log.e("JIA", "error: ${it.message}, $it")
            })
        return Maybe.empty()
    }

}