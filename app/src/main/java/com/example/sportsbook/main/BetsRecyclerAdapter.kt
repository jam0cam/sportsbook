package com.example.sportsbook.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsbook.R
import com.example.sportsbook.databinding.BetListItemBinding

class BetsRecyclerAdapter(
    private val bets: List<Bet>
) : RecyclerView.Adapter<BetsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BetsViewHolder {
        return BetListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let(::BetsViewHolder)
    }

    override fun getItemCount() = bets.size

    override fun onBindViewHolder(holder: BetsViewHolder, position: Int) {
        holder.bind(bets[position])
    }
}