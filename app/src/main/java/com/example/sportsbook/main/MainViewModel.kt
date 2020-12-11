package com.example.sportsbook.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportsbook.MySchedulers
import com.example.sportsbook.interactors.FetchBetsInteractor
import com.example.sportsbook.addTo
import com.example.sportsbook.withSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.joda.time.LocalDate
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val urlInteractor: FetchBetsInteractor,
    private val schedulers: MySchedulers,
) : BaseViewModel() {

    val bets = MutableLiveData<MainUiModel>()
    val state = MutableLiveData<MainUiState>()

    fun init() {
        urlInteractor.getBets()
            .withSchedulers(schedulers)
            .doOnSubscribe { state.value = MainUiState(true) }
            .doFinally { state.value = MainUiState(false) }
            .map(::formatResults)
            .subscribe(bets::postValue, {
                Log.e("JIA", "error: ${it.message}, $it")
            }, {
                Log.e("JIA", "completed with out anything")
            }).addTo(compositeDisposable)
    }

    private fun formatResults(betsMap: Map<LocalDate, List<DailyBet>>): MainUiModel {
        val dates =  betsMap.keys.sorted()
        var startDate = LocalDate.now()
        val dateList = mutableListOf<LocalDate>(startDate)

        while (startDate < dates.lastOrNull() ?: startDate ) {
            startDate = startDate.plusDays(1)
            dateList.add(startDate)
        }

        return MainUiModel(dateList.toList())
    }
}