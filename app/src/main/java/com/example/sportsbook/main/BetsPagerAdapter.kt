package com.example.sportsbook.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.sportsbook.main.bets.BetsFragment
import org.joda.time.LocalDate

class BetsPagerAdapter (
    private val dates: List<LocalDate>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return dates.size
    }

    override fun createFragment(position: Int): Fragment = BetsFragment.newInstance(dates[position])
}