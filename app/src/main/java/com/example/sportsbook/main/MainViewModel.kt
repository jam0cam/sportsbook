package com.example.sportsbook.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportsbook.MySchedulers
import com.example.sportsbook.UrlInteractor
import com.example.sportsbook.addTo
import com.example.sportsbook.withSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val urlInteractor: UrlInteractor,
    private val schedulers: MySchedulers,
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val bets = MutableLiveData<List<DailyBet>>()
    val state = MutableLiveData<MainUiState>()

    fun init() {
        urlInteractor.getBets()
            .withSchedulers(schedulers)
            .doOnSubscribe { state.value = MainUiState(true) }
            .doFinally { state.value = MainUiState(false) }
            .subscribe({
                bets.postValue(it)
            }, {
                Log.e("JIA", "error: ${it.message}, $it")
            }, {
                Log.e("JIA", "completed with out anything")
            }).addTo(compositeDisposable)
    }

}