package com.f3401pal.playground.jg.domain.usecase

import com.f3401pal.playground.jg.domain.model.BalanceSummary
import com.f3401pal.playground.jg.domain.model.TransactionType
import com.f3401pal.playground.jg.repository.db.entity.Transaction
import com.f3401pal.playground.jg.repository.db.entity.type
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