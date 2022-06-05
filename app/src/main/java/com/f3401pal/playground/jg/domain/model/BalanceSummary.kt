package com.f3401pal.playground.jg.domain.model

data class BalanceSummary(
    val totalExpense: Float,
    val totalIncome: Float
) {
    val balance: Float = totalIncome - totalExpense
}