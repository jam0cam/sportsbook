package com.example.sportsbook.dagger

import com.example.sportsbook.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {
    fun inject(activity: MainActivity)
}