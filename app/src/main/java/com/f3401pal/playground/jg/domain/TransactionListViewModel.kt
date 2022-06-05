package com.f3401pal.playground.jg.domain

import androidx.lifecycle.ViewModel
import com.f3401pal.playground.jg.domain.usecase.CalculateBalance
import com.f3401pal.playground.jg.domain.usecase.GetAllTransactions
import com.f3401pal.playground.jg.domain.usecase.GroupDaliyTransactions
import com.f3401pal.playground.jg.ext.CoroutineDispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class TransactionListViewModel @Inject constructor(
    getAllTransactions: GetAllTransactions,
    private val groupDaliyTransactions: GroupDaliyTransactions,
    private val calculateBalance: CalculateBalance,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    private val allTransactions = getAllTransactions.execute()

    val balanceSummary = allTransactions
        .map { calculateBalance.execute(it) }
        .flowOn(coroutineDispatcherProvider.background)

    val daliyTransactions = allTransactions
        .map { groupDaliyTransactions.execute(it) }
        .flowOn(coroutineDispatcherProvider.background)
}