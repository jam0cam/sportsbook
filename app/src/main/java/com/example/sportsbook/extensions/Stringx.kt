package com.example.sportsbook

import java.lang.RuntimeException

/**
 * Throws an exception if string not found
 */
fun String.idxOf(string: String, startIndex: Int) :Int {
    val result = this.indexOf(string, startIndex)

    if (result < 0) {
        throw RuntimeException("not found")
    } else {
        return result
    }
}