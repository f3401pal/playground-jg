package com.f3401pal.playground.jg.repository.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime
import java.time.ZoneId

@RunWith(AndroidJUnit4::class)
class LocalDataTimeConverterTest {

    private val utcTimestamp = 1120898916000L

    private val subject = LocalDataTimeConverter(
        zoneId = ZoneId.of("America/Phoenix")
    )

    @Test
    fun `given a timestamp in UTC, return local data time`() {
        val localDateTime = subject.fromTimestamp(utcTimestamp)

        assertEquals("2005-07-09T01:48:36", localDateTime.toString())
    }

    @Test
    fun `given a local date time, return timestamp in UTC`() {
        val localDateTime = LocalDateTime.of(2005, 7, 9, 1, 48, 36)
        val timestamp = subject.toTimestamp(localDateTime)
        assertEquals(utcTimestamp, timestamp)
    }
}