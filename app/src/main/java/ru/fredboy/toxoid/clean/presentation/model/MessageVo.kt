package ru.fredboy.toxoid.clean.presentation.model

data class MessageVo(
    val text: String,
    val date: String,
    val isFromUser: Boolean
)