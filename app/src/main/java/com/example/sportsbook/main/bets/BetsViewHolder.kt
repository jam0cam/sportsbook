package com.example.sportsbook.main.bets

import com.example.sportsbook.databinding.BetListItemBinding
import com.example.sportsbook.main.Bet

class BetsViewHolder(private val binding: BetListItemBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
    fun bind(bet: BetsListItem.BetItem) {
        binding.teamName.text = bet.name
        binding.odds.text = bet.odds
    }
}