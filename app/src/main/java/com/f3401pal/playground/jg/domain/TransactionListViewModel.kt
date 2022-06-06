package com.f3401pal.playground.jg.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f3401pal.playground.jg.domain.usecase.CalculateBalance
import com.f3401pal.playground.jg.domain.usecase.DeleteTransaction
import com.f3401pal.playground.jg.domain.usecase.GetAllTransactions
import com.f3401pal.playground.jg.domain.usecase.GroupDaliyTransactions
import com.f3401pal.playground.jg.ext.CoroutineDispatcherProvider
import com.f3401pal.playground.jg.repository.db.entity.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionListViewModel @Inject constructor(
    getAllTransactions: GetAllTransactions,
    private val groupDaliyTransactions: GroupDaliyTransactions,
    private val calculateBalance: CalculateBalance,
    private val deleteTransaction: DeleteTransaction,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    private val allTransactions = getAllTransactions.execute()

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            deleteTransaction.execute(transaction)
        }
    }

    val balanceSummary = allTransactions
        .map { calculateBalance.execute(it) }
        .flowOn(coroutineDispatcherProvider.background)

    val daliyTransactions = allTransactions
        .map { groupDaliyTransactions.execute(it) }
        .flowOn(coroutineDispatcherProvider.background)
}