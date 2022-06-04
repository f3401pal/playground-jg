package com.f3401pal.playground.jg.usecase

import androidx.core.text.isDigitsOnly
import com.f3401pal.playground.jg.db.entity.Transaction
import com.f3401pal.playground.jg.ext.CoroutineDispatcherProvider
import com.f3401pal.playground.jg.model.TransactionType
import com.f3401pal.playground.jg.repository.TransactionRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class CreateNewTransaction @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) {

    fun execute(
        type: TransactionType,
        description: String?,
        amount: String?
    ) = flow {
        val validationResult = validateInputs(description, amount)
        Timber.d("validationResult $validationResult")
        val valid = validationResult == TransactionInputValidationResult.Clear
        emit(validationResult)
        if(valid) {
            val signedAmount = when(type) {
                TransactionType.Income -> amount?.toFloat() ?: 0f
                TransactionType.Expense -> 0f - (amount?.toFloat() ?: 0f)
            }
            val transaction = Transaction(
                description = description.orEmpty(),
                amount = signedAmount
            )
            transactionRepository.addNewTransaction(transaction)
        }
    }.flowOn(coroutineDispatcherProvider.io)

    private fun validateInputs(description: String?, amount: String?): TransactionInputValidationResult {
        return when {
            description.isNullOrEmpty() -> TransactionInputValidationResult.EmptyDescription
            amount.isNullOrEmpty() -> TransactionInputValidationResult.EmptyAmount
            !amount.isDigitsOnly() -> TransactionInputValidationResult.InvalidAmount
            else -> TransactionInputValidationResult.Clear
        }
    }
}

enum class TransactionInputValidationResult {
    EmptyDescription, EmptyAmount, InvalidAmount, Clear
}