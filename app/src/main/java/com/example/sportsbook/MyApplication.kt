package com.example.sportsbook

import android.app.Application
import com.example.sportsbook.dagger.ApplicationComponent
import com.example.sportsbook.dagger.DaggerApplicationComponent

class MyApplication : Application() {

    val appComponent = DaggerApplicationComponent.create()

    companion object {
        lateinit var instance: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}