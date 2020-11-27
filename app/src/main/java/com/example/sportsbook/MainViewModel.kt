package com.example.sportsbook

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val urlInteractor: UrlInteractor
) : ViewModel() {

    fun init() {
        urlInteractor.getBets()
    }

}