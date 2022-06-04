package com.f3401pal.playground.jg.model

import androidx.lifecycle.ViewModel
import com.f3401pal.playground.jg.db.entity.Transaction
import com.f3401pal.playground.jg.usecase.GetDaliyTransactions
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class TransactionListViewModel @Inject constructor(
    private val getDaliyTransactions: GetDaliyTransactions
) : ViewModel() {

    val allTransactions = getDaliyTransactions.execute()
}

sealed interface TransactionItem
data class DateHeader(val date: LocalDate) : TransactionItem
data class SingleTransaction(val transaction: Transaction) : TransactionItem