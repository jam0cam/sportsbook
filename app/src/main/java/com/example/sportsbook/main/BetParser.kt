package com.example.sportsbook.main

import com.example.sportsbook.idxOf
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject

class BetParser @Inject constructor() {

    var dateFormatter = DateTimeFormat.forPattern("MMMM dd, yyyy")

    fun parse(category: Category, payload: String) : List<DailyBet> {
        var startIdx = 0

        var dates = getDates(payload)
        var dailyBets = mutableListOf<DailyBet>()
        val list = mutableListOf<Bet>()

        for (i in dates.indices) {
            try {
                startIdx = payload.indexOf(dates[i])
                list.clear()
                val endIdx =
                    if (i == dates.size-1) {Integer.MAX_VALUE}
                    else payload.indexOf(dates[i + 1])

                while(startIdx < endIdx) {
                    val result = parseGame(payload, startIdx)
                    list.addAll(result.second)
                    startIdx = result.first
                }

                val newList = mutableListOf<Bet>()
                newList.addAll(list)
                dailyBets.add(DailyBet(category, LocalDate.parse(dates[i], dateFormatter), newList))

            } catch (e: Exception) {
                //this means we are done iterating through the list
            }

        }

        if (list.isNotEmpty()) {
            dailyBets.add(DailyBet(category, LocalDate.parse(dates.last(), dateFormatter), list))
        }

        return dailyBets
    }

    private fun getDates(payload: String): List<String> {
        val token = "<div class=\"col-xs-12 date\">"
        var idx1 = payload.indexOf(token, 0)
        var idx2 = 0
        var dates = mutableListOf<String>()
        while (idx1 >= 0) {
            idx2 = payload.indexOf("</div>", idx1)
            var date = payload.substring(idx1 + token.length, idx2)
            dates.add(date.trim())
            idx1 = payload.indexOf(token, idx2)
        }

        return dates
    }

    private fun parseGame(payload: String, startIdx: Int) : Pair<Int, List<Bet>> {

        val result = getBet(payload, "firstTeamName", startIdx)
        val result2 = getBet(payload, "secondTeamName", result.first)

        return result2.first to listOf(result.second, result2.second)
    }

    private fun getBet(payload: String, teamName: String, startIdx: Int) : Pair<Int, Bet> {

//    Get the first team
        var token = "id=\"$teamName\">"
        var idx1 = payload.idxOf(token, startIdx)
        var idx2 = payload.idxOf("</span>", idx1)

        var team = payload.substring(idx1 + token.length, idx2)

//        Get the bet
        token = "<div class=\"market\">"

        //skip to the 3rd instance
        idx1 = payload.idxOf(token, idx1 + 1)
        idx1 = payload.idxOf(token, idx1 + 1)
        idx1 = payload.idxOf(token, idx1 + 1)
        idx2 = payload.idxOf("</div>", idx1)
        var bet = payload.substring(idx1 + token.length, idx2)

        return idx2 to Bet(team, bet)
    }

}