package com.example.sportsbook.main.bets

import com.example.sportsbook.main.Category
import org.joda.time.LocalDate

data class BetUiModel(
    val date: LocalDate,
    val category: Category,
    val name: String,
    val odds: String
)