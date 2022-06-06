package com.f3401pal.playground.jg.domain.usecase

import androidx.core.text.isDigitsOnly
import com.f3401pal.playground.jg.domain.model.TransactionInputValidationResult
import com.f3401pal.playground.jg.domain.model.TransactionType
import com.f3401pal.playground.jg.ext.CoroutineDispatcherProvider
import com.f3401pal.playground.jg.repository.TransactionRepository
import com.f3401pal.playground.jg.repository.db.entity.Transaction
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

/**
 * intend to save a new transaction into the repository
 * - validate inputs and emit error on invalidate inputs
 * - set negative or positive amount based on the transaction type
 */
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
            !amount.isDigitsOnly() || amount.toFloat() == 0f -> TransactionInputValidationResult.InvalidAmount
            else -> TransactionInputValidationResult.Clear
        }
    }
}