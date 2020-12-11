package com.example.sportsbook.main

import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.sportsbook.databinding.BetListItemBinding
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

class BetsViewHolder(private val binding: BetListItemBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
    fun bind(bet: Bet) {
        binding.teamName.text = bet.name
        binding.odds.text = bet.odds
    }
}