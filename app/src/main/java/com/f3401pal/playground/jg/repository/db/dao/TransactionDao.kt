package com.f3401pal.playground.jg.repository.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.f3401pal.playground.jg.repository.db.entity.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @androidx.room.Transaction
    @Insert
    fun insertTransaction(transaction: Transaction)

    @androidx.room.Transaction
    @Query("SELECT * FROM `transaction` ORDER BY timestamp DESC")
    fun queryTransactions(): Flow<List<Transaction>>
}