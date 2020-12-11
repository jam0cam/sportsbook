package com.example.sportsbook.persistence

import com.example.sportsbook.main.DailyBet
import org.joda.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BetsCache @Inject constructor() : HasInMemoryCache<LocalDate, List<DailyBet>>()