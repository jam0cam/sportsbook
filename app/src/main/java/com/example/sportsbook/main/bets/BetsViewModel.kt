package com.example.sportsbook.main.bets

import androidx.lifecycle.MutableLiveData
import com.example.sportsbook.interactors.GetBetsFromCacheInteractor
import com.example.sportsbook.main.BaseViewModel
import com.example.sportsbook.main.Category
import org.joda.time.LocalDate
import javax.inject.Inject

class BetsViewModel @Inject constructor(
    private val getBetsFromCacheInteractor: GetBetsFromCacheInteractor
) : BaseViewModel() {

    val bets = MutableLiveData<List<BetsListItem>>()

    fun init(date: LocalDate) {
        val tempBets : MutableList<BetsListItem> = getBetsFromCacheInteractor
            .getBets(date)
            .map { BetsListItem.BetItem(it.category, it.name, it.odds) }
            .toMutableList()

        val idx =  tempBets.indexOfFirst { it is BetsListItem.BetItem && it.category == Category.NCAAF }
        if (idx >= 0) {
            tempBets.add(idx, BetsListItem.Header(Category.NCAAF.value))
        }

        bets.postValue(tempBets)
    }

}