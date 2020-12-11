package com.example.sportsbook.extensions

import android.os.Bundle
import androidx.fragment.app.Fragment

inline fun <T : Fragment> T.applyBundle(block: Bundle.() -> Unit): T {
    if (arguments == null) {
        arguments = Bundle()
    }
    block(arguments!!)
    return this
}
