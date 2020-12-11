package com.example.sportsbook.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.sportsbook.MyApplication
import com.example.sportsbook.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
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

        with (viewModel) {
            state.observe(this@MainActivity, ::updateState)
            bets.observe(this@MainActivity, ::bindBets)
        }
    }

    private fun updateState(state: MainUiState) {
        Log.e("JIA", "updating state : ${state.loading}")
    }

    private fun bindBets(model: MainUiModel) {
        Log.e("JIA", "binding bets $model")

        binding.pager.adapter = BetsPagerAdapter(model.dates, supportFragmentManager, lifecycle)
        TabLayoutMediator(binding.tab, binding.pager) { tab, position ->
            tab.text = model.dates[position].toString()
        }.attach()

    }

}