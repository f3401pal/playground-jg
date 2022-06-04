package com.f3401pal.playground.jg.model

import androidx.lifecycle.ViewModel
import com.f3401pal.playground.jg.db.entity.Transaction
import com.f3401pal.playground.jg.ext.CoroutineDispatcherProvider
import com.f3401pal.playground.jg.usecase.CalculateBalance
import com.f3401pal.playground.jg.usecase.GetAllTransactions
import com.f3401pal.playground.jg.usecase.GroupDaliyTransactions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.time.LocalDate
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