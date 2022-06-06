package com.f3401pal.playground.jg.repository

import com.f3401pal.playground.jg.repository.db.AppDatabase
import com.f3401pal.playground.jg.repository.db.entity.Transaction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * interface to abstract the interactions to the transaction from the implementation
 */
interface TransactionRepository {

    fun deleteTransaction(transaction: Transaction): Boolean
    fun addNewTransaction(transaction: Transaction)
    fun getTransactions(): Flow<List<Transaction>>

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

    override fun getTransactions(): Flow<List<Transaction>> {
        return dao.queryTransactions()
    }


}