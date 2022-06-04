package com.f3401pal.playground.jg.mock

import com.f3401pal.playground.jg.ext.CoroutineDispatcherProvider
import kotlinx.coroutines.test.UnconfinedTestDispatcher

class TestCoroutineDispatcherProvider : CoroutineDispatcherProvider {

    private val testDispatcher = UnconfinedTestDispatcher()

    override val background = testDispatcher
    override val io = testDispatcher
    override val ui = testDispatcher
}