package com.f3401pal.playground.jg.domain.usecase

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.f3401pal.playground.jg.fixture.createTestTransaction
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class GroupDailyTransactionsTest {

    private val subject = GroupDaliyTransactions()

    @Test
    fun `given 2 transitions in difference dates, return list with 2 DaliyTransactions`() {
        val today = LocalDateTime.now()
        val yesterday = today.minusDays(1)
        val transactions = listOf(
            createTestTransaction(dataTime = today),
            createTestTransaction(dataTime = yesterday)
        )
        val transactionPagedList = TransactionPagedList(
            transactions,
            1
        )
        val result = subject.execute(transactionPagedList)

        assertEquals(2, result.dailyTransactions.size)
    }

    @Test
    fun `given 2 transitions in difference dates return list sorted by time descendently`() {
        val today = LocalDateTime.now()
        val yesterday = today.minusDays(1)
        val transactions = listOf(
            createTestTransaction(dataTime = today),
            createTestTransaction(dataTime = yesterday)
        )
        val transactionPagedList = TransactionPagedList(
            transactions,
            1
        )
        val result = subject.execute(transactionPagedList)

        assertEquals(today.toLocalDate(), result.dailyTransactions.first().localDate)
        assertEquals(yesterday.toLocalDate(), result.dailyTransactions.last().localDate)
    }
}