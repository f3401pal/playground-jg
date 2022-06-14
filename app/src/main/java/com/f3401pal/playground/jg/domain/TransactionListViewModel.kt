package com.f3401pal.playground.jg.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f3401pal.playground.jg.domain.usecase.DeleteTransaction
import com.f3401pal.playground.jg.domain.usecase.GetAllTransactions
import com.f3401pal.playground.jg.domain.usecase.GroupDaliyTransactions
import com.f3401pal.playground.jg.ext.CoroutineDispatcherProvider
import com.f3401pal.playground.jg.repository.db.entity.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * shared view model for transaction list and balance summary
 */
@HiltViewModel
class TransactionListViewModel @Inject constructor(
    getAllTransactions: GetAllTransactions,
    private val groupDailyTransactions: GroupDaliyTransactions,
    private val deleteTransaction: DeleteTransaction,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    // empty flow with
    // 0 buffer: if view does not subscribe(gone), ignore the error
    // 0 replay: error does not stay when view is recreated
    private val _error = MutableSharedFlow<Throwable>()
    val error = _error

    // share all transaction flow with balanceSummary and dailyTransactions so we do not need to keep 2 separated flows for each
    private val allTransactions = getAllTransactions.execute()

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            try {
                deleteTransaction.execute(transaction)
            } catch (e: Exception) {
                _error.emit(e)
            }
        }
    }

    val dailyTransactions = allTransactions
        .map { groupDailyTransactions.execute(it) }
        .flowOn(coroutineDispatcherProvider.background)
}