package com.f3401pal.playground.jg.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

fun <T> Flow<T>.collectAfterCreated(lifecycleOwner: LifecycleOwner, runBlock: (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launchWhenCreated {
        collect { runBlock(it) }
    }
}

interface CoroutineDispatcherProvider {
    val background: CoroutineDispatcher
    val io: CoroutineDispatcher
    val ui: CoroutineDispatcher
}