package com.f3401pal.playground.jg.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f3401pal.playground.jg.domain.usecase.DeleteTransaction
import com.f3401pal.playground.jg.domain.usecase.GetAllTransactions
import com.f3401pal.playground.jg.domain.usecase.GroupDaliyTransactions
import com.f3401pal.playground.jg.ext.CoroutineDispatcherProvider
import com.f3401pal.playground.jg.repository.db.entity.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
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

    private val page = MutableStateFlow(1)
    private val allTransactions = page.flatMapLatest { page ->
        getAllTransactions.execute(page)
    }

    fun nextPage() {
        viewModelScope.launch {
            val nextPage = page.value + 1
            page.emit(nextPage)
            Timber.d("next page $nextPage")
        }
    }

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