package ru.fredboy.toxoid.clean.presentation.formatter

import java.text.DateFormat
import javax.inject.Inject

class DateFormats @Inject constructor(
    val dateFormat: DateFormat,
    val timeFormat: DateFormat
)