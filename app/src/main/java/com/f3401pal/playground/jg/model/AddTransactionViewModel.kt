package com.f3401pal.playground.jg.model

import androidx.lifecycle.ViewModel
import com.f3401pal.playground.jg.usecase.CreateNewTransaction
import com.f3401pal.playground.jg.usecase.TransactionInputValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val createNewTransaction: CreateNewTransaction
) : ViewModel() {

    fun saveTransaction(
        type: TransactionType,
        description: String?,
        amount: String?
    ): Flow<TransactionInputValidationResult> {
        Timber.d("saveTransaction $type, $description, $amount")
        return createNewTransaction.execute(
            type, description, amount
        )
    }

}

enum class TransactionType {
    Income, Expense
}