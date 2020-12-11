package com.example.sportsbook.dagger

import com.example.sportsbook.main.BetsFragment
import com.example.sportsbook.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: BetsFragment)
}