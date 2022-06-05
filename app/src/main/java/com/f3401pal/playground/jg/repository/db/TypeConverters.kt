package com.f3401pal.playground.jg.repository.db

import androidx.room.TypeConverter
import com.f3401pal.playground.jg.ext.toUtcEpochMilli
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class LocalDataTimeConverter(
    private val zoneId: ZoneId = ZoneId.systemDefault()
) {

    @TypeConverter
    fun fromTimestamp(timestamp: Long): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), zoneId)
    }

    @TypeConverter
    fun toTimestamp(dateTime: LocalDateTime): Long {
        return dateTime.toUtcEpochMilli(zoneId)
    }
}