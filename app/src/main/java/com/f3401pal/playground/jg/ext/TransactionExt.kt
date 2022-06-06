package com.f3401pal.playground.jg.ext

import com.f3401pal.playground.jg.domain.model.TransactionType
import com.f3401pal.playground.jg.repository.db.entity.Transaction
import java.time.LocalDate
import kotlin.math.sign

fun Transaction.type(): TransactionType = if(amount.sign < 0) TransactionType.Expense else TransactionType.Income

fun Transaction.displayAmount() = amount.toDollarFormat()

fun Transaction.date(): LocalDate = timestamp.toLocalDate()