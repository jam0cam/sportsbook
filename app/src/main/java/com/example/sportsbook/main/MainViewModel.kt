package com.example.sportsbook.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportsbook.MySchedulers
import com.example.sportsbook.interactors.FetchBetsInteractor
import com.example.sportsbook.addTo
import com.example.sportsbook.extensions.postEvent
import com.example.sportsbook.utils.Event
import com.example.sportsbook.withSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.joda.time.LocalDate
import java.lang.RuntimeException
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val urlInteractor: FetchBetsInteractor,
    private val schedulers: MySchedulers,
) : BaseViewModel() {

    val bets = MutableLiveData<MainUiModel>()
    val state = MutableLiveData<MainUiState>()
    val errorMsg = MutableLiveData<Event<String>>()

    fun init() {
        compositeDisposable.clear()
        urlInteractor.getBets()
            .withSchedulers(schedulers)
            .doOnSubscribe { state.value = MainUiState(true) }
            .doFinally { state.value = MainUiState(false) }
            .map(::formatResults)
            .subscribe(bets::postValue, {
                Log.d("JIA", "error: ${it.message}, $it")
                errorMsg.postEvent(it.message ?: "an error occurred")
            }, {
                Log.d("JIA", "completed with out anything")
            }).addTo(compositeDisposable)
    }

    private fun formatResults(betsMap: Map<LocalDate, List<DailyBet>>): MainUiModel {
        if (1==1) throw RuntimeException("hello world")
        return MainUiModel(betsMap.keys.sorted().filter { it < LocalDate.now().plusDays(2) })
    }
}