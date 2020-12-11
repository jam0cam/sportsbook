package com.example.sportsbook.extensions

import android.view.View

/**
 * Calls the specified block if the object is null.
 */
@Suppress("unused")
inline fun <T> T?.switchIfNull(block: () -> T): T {
    return this ?: block()
}

/**
 * Runs the specified block if the object is null.
 */
@Suppress("unused")
inline fun <T> T?.runIfNull(block: () -> Unit): T? {
    if (this == null) block()
    return this
}

inline fun View.showIfOrGone(predicate: (View) -> Boolean) {
    if (predicate(this)) {
        this.show()
    } else {
        this.gone()
    }
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.show() {
    this.visibility = View.VISIBLE
}
