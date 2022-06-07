package com.f3401pal.playground.jg.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class BalanceSummaryTest {

    @Test
    fun `given totalIncome and totalExpense, calculate balance`() {
        val balanceSummary = BalanceSummary(20f, 200f)
        assertEquals(180f, balanceSummary.balance)
    }

    @Test
    fun `given zero totalIncome and totalExpense, calculate balance`() {
        val balanceSummary = BalanceSummary(0f, 0f)
        assertEquals(0f, balanceSummary.balance)
    }

    @Test
    fun `given totalIncome and totalExpense calculate balance percentage`() {
        val balanceSummary = BalanceSummary(20f, 200f)
        assertEquals(10, balanceSummary.balancePercentage)
    }

    @Test
    fun `given zero totalIncome and totalExpense, calculate balance percentage`() {
        val balanceSummary = BalanceSummary(0f, 0f)
        assertEquals(0, balanceSummary.balancePercentage)
    }
}