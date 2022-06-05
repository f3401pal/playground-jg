package com.f3401pal.playground.jg.ext

import java.time.LocalDateTime
import java.time.ZoneId

fun LocalDateTime.toUtcEpochMilli(zoneId: ZoneId): Long {
    return atZone(zoneId).toInstant().toEpochMilli()
}