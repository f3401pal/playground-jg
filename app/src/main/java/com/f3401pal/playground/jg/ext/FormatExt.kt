package com.f3401pal.playground.jg.ext

import kotlin.math.absoluteValue
import kotlin.math.sign

fun Float.toDollarFormat(): String {
    return when {
        sign < 0 -> "-\$$absoluteValue"
        else -> "\$$absoluteValue"
    }
}