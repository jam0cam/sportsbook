package com.example.sportsbook.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.sportsbook.MyApplication
import com.example.sportsbook.R
import com.example.sportsbook.dagger.ViewModelFactory
import com.example.sportsbook.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as MyApplication).appComponent.inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        viewModel.init()

        with (viewModel) {
            state.observe(this@MainActivity, ::updateState)
            bets.observe(this@MainActivity, ::bindBets)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.init()
        return true
    }

    private fun updateState(state: MainUiState) {
        Log.d("JIA", "updating state : ${state.loading}")
        if (state.loading) binding.progressBar.show()
        else binding.progressBar.hide()
    }

    private fun bindBets(model: MainUiModel) {
        Log.d("JIA", "binding bets $model")

        binding.pager.adapter = BetsPagerAdapter(model.dates, supportFragmentManager, lifecycle)
        TabLayoutMediator(binding.tab, binding.pager) { tab, position ->
            tab.text = model.dates[position].toString()
        }.attach()

    }

}