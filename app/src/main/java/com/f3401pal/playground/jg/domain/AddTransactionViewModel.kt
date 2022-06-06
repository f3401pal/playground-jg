package com.f3401pal.playground.jg.domain

import androidx.lifecycle.ViewModel
import com.f3401pal.playground.jg.domain.model.TransactionInputValidationResult
import com.f3401pal.playground.jg.domain.model.TransactionType
import com.f3401pal.playground.jg.domain.usecase.CreateNewTransaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

/**
 * view model used by the add transaction bottom sheet
 */
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