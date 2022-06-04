package com.f3401pal.playground.jg.ext

import java.time.LocalDateTime
import java.time.ZoneId

fun LocalDateTime.toUtcEpochMilli(): Long {
    return atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}