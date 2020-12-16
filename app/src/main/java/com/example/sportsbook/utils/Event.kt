package com.example.sportsbook.utils


/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 *
 * Wrap values with Event to ensure they are consumed once, not on every observer registration.
 */
open class Event<out T>(private val content: T? = null) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled || content == null) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T? = content
}