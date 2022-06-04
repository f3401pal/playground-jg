package com.f3401pal.playground.jg.usecase

import com.f3401pal.playground.jg.db.entity.Transaction
import com.f3401pal.playground.jg.db.entity.type
import com.f3401pal.playground.jg.model.TransactionType
import javax.inject.Inject
import kotlin.math.absoluteValue

class CalculateBalance @Inject constructor() {

    fun execute(transactions: List<Transaction>): BalanceSummary {
        var totalExpense = 0f
        var totalIncome = 0f
        transactions.forEach {
            when(it.type()) {
                TransactionType.Expense -> totalExpense += it.amount.absoluteValue
                TransactionType.Income -> totalIncome += it.amount.absoluteValue
            }
        }
        return BalanceSummary(
            totalExpense, totalIncome
        )
    }
}

data class BalanceSummary(
    val totalExpense: Float,
    val totalIncome: Float
) {
    val balance: Float = totalIncome - totalExpense
}