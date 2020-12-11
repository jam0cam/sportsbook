package com.example.sportsbook.main.bets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsbook.databinding.BetListHeaderBinding
import com.example.sportsbook.databinding.BetListItemBinding

class BetsRecyclerAdapter(
    private val bets: List<BetsListItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.REGULAR_ITEM.ordinal ->   BetListItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
                .let(::BetsViewHolder)
            else -> BetListHeaderBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
                .let(::HeaderViewHolder)
        }
    }

    override fun getItemCount() = bets.size

    override fun getItemViewType(position: Int): Int {
        return when (bets[position]) {
            is BetsListItem.BetItem -> ViewType.REGULAR_ITEM.ordinal
            is BetsListItem.Header -> ViewType.HEADER.ordinal
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = bets[position]

        when (holder) {
            is BetsViewHolder -> holder.bind(model as BetsListItem.BetItem)
            is HeaderViewHolder -> holder.bind(model as BetsListItem.Header)
        }
    }

    enum class ViewType {
        REGULAR_ITEM,
        HEADER,
    }
}