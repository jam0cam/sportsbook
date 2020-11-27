package com.example.sportsbook

import java.lang.Exception
import javax.inject.Inject

class BetParser @Inject constructor() {

    fun parse(string: String) : List<Bet> {
        val list = mutableListOf<Bet>()
        var startIdx = 0

        try {
            while(true) {
                val result = parseGame(string, startIdx)
                list.addAll(result.second)
                startIdx = result.first
            }
        } catch (e: Exception) {
            //this means we are done iterating through the list
        }

        return list
    }

    private fun parseGame(payload: String, startIdx : Int) : Pair<Int, List<Bet>> {

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
        idx1 = payload.idxOf(token, idx1+1)
        idx1 = payload.idxOf(token, idx1+1)
        idx1 = payload.idxOf(token, idx1+1)
        idx2 = payload.idxOf("</div>", idx1)
        var bet = payload.substring(idx1 + token.length, idx2)

        return idx2 to Bet(team, bet)
    }

}