package com.example.sportsbook

import android.app.Application
import com.example.sportsbook.dagger.ApplicationComponent
import com.example.sportsbook.dagger.DaggerApplicationComponent

class MyApplication : Application() {

    val appComponent = DaggerApplicationComponent.create()

}