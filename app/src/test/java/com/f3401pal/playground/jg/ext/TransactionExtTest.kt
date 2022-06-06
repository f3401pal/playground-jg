package com.f3401pal.playground.jg.ext

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.f3401pal.playground.jg.domain.model.TransactionType
import com.f3401pal.playground.jg.fixture.createTestTransaction
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TransactionExtTest {

    @Test
    fun `given negative amount, return expense transaction type`() {
        val subject = createTestTransaction(
            amount = -10f
        )
        assertEquals(TransactionType.Expense, subject.type())
    }

    @Test
    fun `given positive amount, return expense transaction type`() {
        val subject = createTestTransaction(
            amount = 10f
        )
        assertEquals(TransactionType.Income, subject.type())
    }

    @Test
    fun `given negative amount, return expected display amount string`() {
        val subject = createTestTransaction(
            amount = -10f
        )
        assertEquals("-$10.0", subject.displayAmount())
    }

    @Test
    fun `given positive amount, return expected display amount string`() {
        val subject = createTestTransaction(
            amount = 10f
        )
        assertEquals("$10.0", subject.displayAmount())
    }
}