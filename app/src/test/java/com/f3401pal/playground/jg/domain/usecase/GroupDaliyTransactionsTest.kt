package com.f3401pal.playground.jg.domain.usecase

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.f3401pal.playground.jg.fixture.createTestTransaction
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class GroupDaliyTransactionsTest {

    private val subject = GroupDaliyTransactions()

    @Test
    fun `given 2 transitions in difference dates, return list with 2 DaliyTransactions`() {
        val today = LocalDateTime.now()
        val yesterday = today.minusDays(1)
        val transactions = listOf(
            createTestTransaction(dataTime = today),
            createTestTransaction(dataTime = yesterday)
        )
        val result = subject.execute(transactions)

        assertEquals(2, result.size)
    }

    @Test
    fun `given 2 transitions in difference dates return list sorted by time descendently`() {
        val today = LocalDateTime.now()
        val yesterday = today.minusDays(1)
        val transactions = listOf(
            createTestTransaction(dataTime = today),
            createTestTransaction(dataTime = yesterday)
        )
        val result = subject.execute(transactions)

        assertEquals(today.toLocalDate(), result.first().localDate)
        assertEquals(yesterday.toLocalDate(), result.last().localDate)
    }
}