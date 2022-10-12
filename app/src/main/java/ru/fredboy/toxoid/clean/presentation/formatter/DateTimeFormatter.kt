package ru.fredboy.toxoid.clean.presentation.formatter

import java.util.*
import javax.inject.Inject

class DateTimeFormatter @Inject constructor(
    private val dateFormats: DateFormats,
) {

    fun formatDate(date: Date): String {
        return dateFormats.dateFormat.format(date)
    }

    fun formatTime(date: Date): String {
        return dateFormats.timeFormat.format(date)
    }

}