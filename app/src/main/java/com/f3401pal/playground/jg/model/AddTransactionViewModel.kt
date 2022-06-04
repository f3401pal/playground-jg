package com.f3401pal.playground.jg.model

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(

) : ViewModel() {

    fun saveTransaction(
        type: TransactionType,
        description: String,
        amount: Float
    ) {
        Timber.d("saveTransaction $type, $description, $amount")

    }

}

sealed interface TransactionType
object Income : TransactionType
object Expense : TransactionType