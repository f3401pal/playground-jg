package com.f3401pal.playground.jg.db

import androidx.room.TypeConverter
import com.f3401pal.playground.jg.ext.toUtcEpochMilli
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class LocalDataTimeConverter {

    @TypeConverter
    fun fromTimestamp(timestamp: Long): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
    }

    @TypeConverter
    fun toTimestamp(dateTime: LocalDateTime): Long {
        return dateTime.toUtcEpochMilli()
    }
}