package ru.fredboy.toxoid.clean.domain.model

import java.util.*

data class Message(
    val id: String,
    val chatId: String,
    val senderId: String,
    val text: String,
    val date: Date
)
