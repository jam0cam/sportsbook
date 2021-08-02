package com.example.sportsbook.dagger

import com.example.sportsbook.main.bets.BetsFragment
import com.example.sportsbook.main.MainActivity
import com.example.sportsbook.sample.SampleActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: BetsFragment)
    fun inject(sampleActivity: SampleActivity)
}