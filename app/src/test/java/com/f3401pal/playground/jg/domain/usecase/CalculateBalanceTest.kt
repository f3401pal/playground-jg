package com.f3401pal.playground.jg.domain.usecase

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.f3401pal.playground.jg.fixture.createTestTransaction
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalculateBalanceTest {

    private val subject = CalculateBalance()

    @Test
    fun `given a list of transitions, calculate total expense correctly`() {
        val transactions = listOf(
            createTestTransaction(amount = -100f),
            createTestTransaction(amount = 200f),
            createTestTransaction(amount = 100f),
            createTestTransaction(amount = -50f),
            createTestTransaction(amount = -150f),
        )
        val result = subject.execute(transactions)

        assertEquals(300f, result.totalExpense)
    }

    @Test
    fun `given a list of transitions, calculate total income correctly`() {
        val transactions = listOf(
            createTestTransaction(amount = 100f),
            createTestTransaction(amount = 200f),
            createTestTransaction(amount = 50f),
            createTestTransaction(amount = -50f),
            createTestTransaction(amount = 150f),
        )
        val result = subject.execute(transactions)

        assertEquals(500f, result.totalIncome)
    }

    @Test
    fun `given a list of transitions, calculate balance correctly`() {
        val transactions = listOf(
            createTestTransaction(amount = 100f),
            createTestTransaction(amount = -200f),
            createTestTransaction(amount = -100f),
            createTestTransaction(amount = 50f),
            createTestTransaction(amount = -50f),
            createTestTransaction(amount = 150f),
        )
        val result = subject.execute(transactions)

        assertEquals(-50f, result.balance)
    }

    @Test
    fun `given a empty list of transitions, return zero total expense, income and balance`() {
        val result = subject.execute(emptyList())

        assertEquals(0f, result.totalExpense)
        assertEquals(0f, result.totalIncome)
        assertEquals(0f, result.balance)
    }
}