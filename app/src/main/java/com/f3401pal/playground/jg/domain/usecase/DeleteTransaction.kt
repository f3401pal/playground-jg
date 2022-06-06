package com.f3401pal.playground.jg.domain.usecase

import com.f3401pal.playground.jg.ext.CoroutineDispatcherProvider
import com.f3401pal.playground.jg.repository.TransactionRepository
import com.f3401pal.playground.jg.repository.db.entity.Transaction
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * delete a transaction from repository
 */
class DeleteTransaction @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) {

    suspend fun execute(transaction: Transaction) = withContext(coroutineDispatcherProvider.io) {
        transactionRepository.deleteTransaction(transaction)
    }
}