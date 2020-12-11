package com.example.sportsbook.main.bets

import com.example.sportsbook.main.Category

sealed class BetsListItem {
    data class BetItem(val category: Category, val name: String, val odds: String) : BetsListItem()
    data class Header(val name: String) : BetsListItem()
}
