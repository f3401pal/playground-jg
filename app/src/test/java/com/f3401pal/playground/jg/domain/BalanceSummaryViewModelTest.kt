package com.f3401pal.playground.jg.domain

import app.cash.turbine.test
import com.f3401pal.playground.jg.domain.model.BalanceSummary
import com.f3401pal.playground.jg.domain.usecase.GetBalance
import com.f3401pal.playground.jg.mock.TestCoroutineDispatcherProvider
import com.f3401pal.playground.jg.mock.mockkRelaxed
import io.mockk.every
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class BalanceSummaryViewModelTest {

    private val balanceSummary = mockkRelaxed<BalanceSummary>()
    private val balanceSummaryFlow = MutableStateFlow(balanceSummary)

    private val getBalance: GetBalance = mockkRelaxed<GetBalance>().apply {
        every {
            execute()
        } returns balanceSummaryFlow
    }
    private val coroutineDispatcherProvider = TestCoroutineDispatcherProvider()

    private val subject = BalanceSummaryViewModel(
        getBalance,
        coroutineDispatcherProvider
    )

    @Test
    fun `given a list of transaction, map to balance summary`() = runBlocking {
        subject.balanceSummary.test {
            assertEquals(balanceSummary, expectMostRecentItem())
            cancelAndConsumeRemainingEvents()
        }
    }
}