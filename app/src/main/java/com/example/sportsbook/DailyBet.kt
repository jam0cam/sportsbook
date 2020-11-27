package com.example.sportsbook

import org.joda.time.DateTime
import org.joda.time.LocalDate

data class DailyBet (val date: LocalDate, val bets: MutableList<Bet>)