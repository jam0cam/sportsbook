package com.example.sportsbook.main

import org.joda.time.LocalDate

data class DailyBet(
    val category: Category,
    val date: LocalDate,
    val bets: MutableList<Bet>
)