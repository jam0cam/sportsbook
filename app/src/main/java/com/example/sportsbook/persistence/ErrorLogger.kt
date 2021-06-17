package com.example.sportsbook.persistence

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorLogger @Inject constructor() {

    private val error = mutableListOf<String>()

    fun log(msg: String) {
        Log.e("JIA", "Adding Log $msg $this")
        error.add(msg)
    }

    fun clearLogs() {
        Log.e("JIA", "clearing logs $this")
        return error.clear()
    }

    fun getLogs(): List<String> {
        Log.e("JIA", "getting logs $this")
        return error.toList()
    }
}