package com.example.sportsbook.main

import org.joda.time.LocalDate

data class DailyBet (val date: LocalDate, val bets: MutableList<Bet>)