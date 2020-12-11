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
        return MainUiModel(betsMap.keys.sorted().filter { it < LocalDate.now().plusDays(2) })
    }

    private fun getLastDate(dates: List<LocalDate>) : LocalDate{
        var date = dates.lastOrNull() ?: LocalDate.now()
        if (date > LocalDate.now().plusDays(2))
            date = LocalDate.now().plusDays(2)

        return date
    }
}