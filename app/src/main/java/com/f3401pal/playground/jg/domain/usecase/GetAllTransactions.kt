package com.f3401pal.playground.jg.domain.usecase

import com.f3401pal.playground.jg.ext.CoroutineDispatcherProvider
import com.f3401pal.playground.jg.repository.TransactionRepository
import com.f3401pal.playground.jg.repository.db.entity.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val PAGE_SIZE = 25L
/**
 * query all transaction from repository
 */
class GetAllTransactions @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) {

    fun execute(page: Int): Flow<TransactionPagedList> {
        return transactionRepository.getTransactions(page * PAGE_SIZE)
            .map {
                TransactionPagedList(
                    it, page
                )
            }.flowOn(coroutineDispatcherProvider.io)
    }

}

data class TransactionPagedList(
    val transactions: List<Transaction>,
    val page: Int
) {
    val hasNextPage = transactions.size >= (page * PAGE_SIZE)
}