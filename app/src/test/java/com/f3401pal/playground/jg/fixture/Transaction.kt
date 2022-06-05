package com.f3401pal.playground.jg.fixture

import com.f3401pal.playground.jg.repository.db.entity.Transaction
import java.time.LocalDateTime

fun createTestTransaction(
    amount: Float = 10f,
    dataTime: LocalDateTime = LocalDateTime.now()
) = Transaction(
    description = "test",
    amount = amount,
    timestamp = dataTime
)