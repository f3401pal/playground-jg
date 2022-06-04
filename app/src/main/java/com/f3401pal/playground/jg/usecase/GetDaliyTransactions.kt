package com.f3401pal.playground.jg.usecase

import com.f3401pal.playground.jg.db.entity.Transaction
import com.f3401pal.playground.jg.db.entity.date
import com.f3401pal.playground.jg.ext.CoroutineDispatcherProvider
import com.f3401pal.playground.jg.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class GetDaliyTransactions @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) {

    fun execute(): Flow<List<DaliyTransactions>> {
        return transactionRepository.getTransactions().map { transitions ->
            // because the transactions are sorted by time, iterate them and insert date label before the first transaction of a day
            transitions.groupBy { it.date() }.map {
                DaliyTransactions(it.key, it.value)
            }.sortedByDescending {
                it.localDate
            }
        }.flowOn(coroutineDispatcherProvider.io)
    }

}

data class DaliyTransactions(
    val localDate: LocalDate,
    val transactions: List<Transaction>
)