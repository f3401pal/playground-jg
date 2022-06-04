package com.f3401pal.playground.jg.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.f3401pal.playground.jg.db.LocalDataTimeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.math.absoluteValue
import kotlin.math.sign

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val description: String,
    val amount: Float,
    @TypeConverters(LocalDataTimeConverter::class)
    val timestamp: LocalDateTime = LocalDateTime.now()
)

fun Transaction.displayAmount() =
    if(amount.sign < 0) "— \$ ${amount.absoluteValue}" else "\$ ${amount.absoluteValue}"

fun Transaction.date(): LocalDate = timestamp.toLocalDate()