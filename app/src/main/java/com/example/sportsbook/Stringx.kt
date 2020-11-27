package com.example.sportsbook

import java.lang.RuntimeException

fun String.idxOf(string: String, startIndex: Int) :Int {
    val result = this.indexOf(string, startIndex)

    if (result < 0) {
        throw RuntimeException("not found")
    } else {
        return result
    }
}