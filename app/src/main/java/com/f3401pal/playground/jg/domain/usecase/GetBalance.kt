package com.f3401pal.playground.jg.domain.usecase

import com.f3401pal.playground.jg.repository.TransactionRepository
import javax.inject.Inject

/**
 * query balances based on a given list of transactions
 */
class GetBalance @Inject constructor(
    private val transactionRepository: TransactionRepository
) {

    fun execute() = transactionRepository.getBalanceSummery()
}