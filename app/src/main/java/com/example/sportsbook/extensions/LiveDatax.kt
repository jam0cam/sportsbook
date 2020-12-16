package com.example.sportsbook.extensions


import androidx.annotation.MainThread
import androidx.lifecycle.*
import com.example.sportsbook.utils.Event
import com.example.sportsbook.utils.EventObserver

/**
 * Wrapper for [EventObserver] to be used with [Event]s.
 *
 * liveData<Event<*>().observeEvent(lifecycleOwner){ action() }
 *
 */
@MainThread
inline fun <T> LiveData<Event<T>>.observeEvent(
    owner: LifecycleOwner,
    crossinline onChanged: (T) -> Unit
): Observer<T> {
    val wrappedObserver = EventObserver<T> { t -> onChanged.invoke(t) }
    observe(owner, wrappedObserver)
    return wrappedObserver as Observer<T>
}

fun <T> MutableLiveData<Event<T>>.postEvent(value: T) {
    postValue(Event(value))
}

inline fun <T> LiveData<T>.valueAsync(lifecycleOwner: LifecycleOwner, crossinline callback: (T) -> Unit) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            t?.let {
                callback(it)
                removeObserver(this)
            }
        }
    })
}

inline fun <T> LiveData<T>.observeFiltered(
    owner: LifecycleOwner,
    crossinline filter: (T) -> Boolean,
    crossinline onChanged: (T) -> Unit
) {
    observe(owner, Observer { t ->
        t?.takeIf(filter)?.let {
            onChanged(it)
        }
    })
}

fun <T> MutableLiveData<T>.immutable(): LiveData<T> = this
