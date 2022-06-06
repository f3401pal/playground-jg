package com.f3401pal.playground.jg.domain.usecase

import com.f3401pal.playground.jg.domain.model.BalanceSummary
import com.f3401pal.playground.jg.domain.model.TransactionType
import com.f3401pal.playground.jg.ext.type
import com.f3401pal.playground.jg.repository.db.entity.Transaction
import javax.inject.Inject
import kotlin.math.absoluteValue

/**
 * calculate balances based on a given list of transactions
 */
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