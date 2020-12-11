package com.example.sportsbook.main.bets

import com.example.sportsbook.databinding.BetListHeaderBinding
import com.example.sportsbook.databinding.BetListItemBinding
import com.example.sportsbook.main.Bet

class HeaderViewHolder(private val binding: BetListHeaderBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
    fun bind(item: BetsListItem.Header) {
        binding.category.text = item.name
    }
}