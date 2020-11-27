package com.example.sportsbook

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val urlInteractor: UrlInteractor,
    private val schedulers: MySchedulers,
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun init() {

        urlInteractor.getBets()
            .withSchedulers(schedulers)
            .subscribe({
                Log.e("JIA", "success $it")
            }, {
                Log.e("JIA", "error: ${it.message}, $it")
            }, {
                Log.e("JIA", "completed with out anything")
            }).addTo(compositeDisposable)
    }

}