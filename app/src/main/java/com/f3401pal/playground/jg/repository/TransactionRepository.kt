package com.f3401pal.playground.jg.repository

import com.f3401pal.playground.jg.domain.model.BalanceSummary
import com.f3401pal.playground.jg.repository.db.AppDatabase
import com.f3401pal.playground.jg.repository.db.entity.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.absoluteValue

/**
 * interface to abstract the interactions to the transaction from the implementation
 */
interface TransactionRepository {

    fun deleteTransaction(transaction: Transaction): Boolean
    fun addNewTransaction(transaction: Transaction)
    fun getTransactions(limit: Long): Flow<List<Transaction>>

    fun getBalanceSummery(): Flow<BalanceSummary>
}

/**
 * Local DB implementation of the TransactionRepository
 */
@Singleton
class TransactionRepositoryImpl @Inject constructor(
    db: AppDatabase
) : TransactionRepository {

    private val dao = db.transactionDao()

    override fun deleteTransaction(transaction: Transaction): Boolean {
        return dao.deleteTransaction(transaction) > 0
    }

    override fun addNewTransaction(transaction: Transaction) {
        dao.insertTransaction(transaction)
    }

    override fun getTransactions(limit: Long): Flow<List<Transaction>> {
        return dao.queryTransactions(limit)
    }

    override fun getBalanceSummery(): Flow<BalanceSummary> {
        return dao.totalIncome().combine(dao.totalExpense()) { income, expense ->
            BalanceSummary(
                expense?.absoluteValue ?: 0f,
                income?.absoluteValue ?: 0f
            )
        }
    }


}