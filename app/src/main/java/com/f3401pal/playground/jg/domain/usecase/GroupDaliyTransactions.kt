package com.f3401pal.playground.jg.domain.usecase

import com.f3401pal.playground.jg.domain.model.DaliyTransactions
import com.f3401pal.playground.jg.repository.db.entity.Transaction
import com.f3401pal.playground.jg.repository.db.entity.date
import javax.inject.Inject

class GroupDaliyTransactions @Inject constructor() {

    fun execute(transactions: List<Transaction>): List<DaliyTransactions> {
        // because the transactions are sorted by time, iterate them and insert date label before the first transaction of a day
        return transactions.groupBy { it.date() }.map {
            DaliyTransactions(it.key, it.value)
        }.sortedByDescending {
            it.localDate
        }
    }

}