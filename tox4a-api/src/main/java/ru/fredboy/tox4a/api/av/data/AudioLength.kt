package ru.fredboy.tox4a.api.av.data

import kotlin.time.Duration
import kotlin.time.Duration.Companion.microseconds

enum class AudioLength(val value: Duration) {
    LENGTH_2500(2_500.microseconds),
    LENGTH_5000(5_000.microseconds),
    LENGTH_10000(10_000.microseconds),
    LENGTH_20000(20_000.microseconds),
    LENGTH_40000(40_000.microseconds),
    LENGTH_60000(60_000.microseconds)
}