package com.example.sportsbook.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.sportsbook.MyApplication
import com.example.sportsbook.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as MyApplication).appComponent.inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.init()
        binding.swipeRefresh.setOnRefreshListener { viewModel.init() }

        with (viewModel) {
            state.observe(this@MainActivity, ::updateState)
            bets.observe(this@MainActivity, ::bindBets)
        }
    }

    private fun updateState(state: MainUiState) {
        Log.e("JIA", "updating state : ${state.loading}")
        binding.swipeRefresh.isRefreshing = state.loading
    }

    private fun bindBets(bets: List<DailyBet>) {
        Log.e("JIA", "binding bets $bets")
    }

}