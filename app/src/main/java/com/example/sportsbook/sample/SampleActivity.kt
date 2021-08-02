package com.example.sportsbook.sample

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.sportsbook.MyApplication
import com.example.sportsbook.dagger.ViewModelFactory
import com.example.sportsbook.databinding.ActivityMainBinding
import com.example.sportsbook.databinding.ActivitySampleBinding
import com.example.sportsbook.extensions.observeEvent
import com.example.sportsbook.main.BaseViewModel
import com.example.sportsbook.main.MainViewModel
import javax.inject.Inject

class SampleActivity  : AppCompatActivity() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: SampleViewModel by viewModels { viewModelFactory }

    private lateinit var binding: ActivitySampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as MyApplication).appComponent.inject(this)

        binding = ActivitySampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

       binding.testBtn.setOnClickListener {
           viewModel.makeApiCall()
       }
    }
}