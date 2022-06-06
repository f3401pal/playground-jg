package com.f3401pal.playground.jg.repository.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.f3401pal.playground.jg.repository.db.LocalDataTimeConverter
import java.time.LocalDateTime

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val description: String,
    val amount: Float,
    @TypeConverters(LocalDataTimeConverter::class)
    val timestamp: LocalDateTime = LocalDateTime.now()
)