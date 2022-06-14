package com.f3401pal.playground.jg.repository.db.dao

import androidx.room.Dao
import androidx.room.Delete
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

    @androidx.room.Transaction
    @Query("SELECT SUM(amount) FROM `transaction` WHERE amount > 0")
    fun totalIncome(): Flow<Float?>

    @androidx.room.Transaction
    @Query("SELECT SUM(amount) FROM `transaction` WHERE amount < 0")
    fun totalExpense(): Flow<Float?>

    @androidx.room.Transaction
    @Delete
    fun deleteTransaction(transaction: Transaction): Int
}