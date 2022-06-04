package com.f3401pal.playground.jg.repository

import com.f3401pal.playground.jg.db.AppDatabase
import com.f3401pal.playground.jg.db.entity.Transaction
import javax.inject.Inject
import javax.inject.Singleton

interface TransactionRepository {

    fun addNewTransaction(transaction: Transaction)

}

@Singleton
class TransactionRepositoryImpl @Inject constructor(
    db: AppDatabase
) : TransactionRepository {

    private val dao = db.transactionDao()

    override fun addNewTransaction(transaction: Transaction) {
        dao.insertTransaction(transaction)
    }


}