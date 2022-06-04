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

class GetAllTransactions @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) {

    fun execute(): Flow<List<Transaction>> {
        return transactionRepository.getTransactions().flowOn(coroutineDispatcherProvider.io)
    }

}