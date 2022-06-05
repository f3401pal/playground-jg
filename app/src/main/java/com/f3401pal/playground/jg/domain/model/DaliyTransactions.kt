package com.f3401pal.playground.jg.domain.model

import com.f3401pal.playground.jg.repository.db.entity.Transaction
import java.time.LocalDate

data class DaliyTransactions(
    val localDate: LocalDate,
    val transactions: List<Transaction>
)