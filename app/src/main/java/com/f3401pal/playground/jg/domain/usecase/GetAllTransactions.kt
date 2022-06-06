package com.f3401pal.playground.jg.domain.usecase

import com.f3401pal.playground.jg.ext.CoroutineDispatcherProvider
import com.f3401pal.playground.jg.repository.TransactionRepository
import com.f3401pal.playground.jg.repository.db.entity.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * query all transaction from repository
 */
class GetAllTransactions @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) {

    fun execute(): Flow<List<Transaction>> {
        return transactionRepository.getTransactions().flowOn(coroutineDispatcherProvider.io)
    }

}