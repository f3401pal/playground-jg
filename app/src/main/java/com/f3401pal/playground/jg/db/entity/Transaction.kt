package com.f3401pal.playground.jg.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.f3401pal.playground.jg.db.LocalDataTimeConverter
import java.time.LocalDateTime

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = -1,
    val description: String,
    val amount: Float,
    @TypeConverters(LocalDataTimeConverter::class)
    val timestamp: LocalDateTime = LocalDateTime.now()
)
