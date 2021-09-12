package com.example.rickyandmorty_finalproject_anderson.presentation.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.concurrent.atomic.AtomicBoolean

class Event<T>(val data: T) {

    var consumed: AtomicBoolean = AtomicBoolean(false)
        private set

    fun consume(consumer: (T) -> Unit) {
        if (consumed.get().not()) {
            consumer(data)
            consumed = AtomicBoolean(true)
        }
    }
}

typealias LiveEvent<T> = LiveData<Event<T>>

typealias MutableLiveEvent<T> = MutableLiveData<Event<T>>

fun <T> MutableLiveEvent<T>.send(data: T) = postValue(Event(data))

fun <T> LiveEvent<T>.consume(lifecycleOwner: LifecycleOwner, consumer: (T) -> Unit) {
    observe(lifecycleOwner, { it.consume(consumer) })
}
