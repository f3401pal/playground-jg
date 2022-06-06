package com.f3401pal.playground.jg.domain.model

import com.f3401pal.playground.jg.repository.db.entity.Transaction
import java.time.LocalDate

/**
 * data model to represent transactions on a specific date
 */
data class DailyTransactions(
    val localDate: LocalDate, // a specific date in local timezone
    val transactions: List<Transaction> // all transactions happened on the date
)