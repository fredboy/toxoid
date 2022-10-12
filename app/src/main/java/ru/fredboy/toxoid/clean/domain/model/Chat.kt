package ru.fredboy.toxoid.clean.domain.model

data class Chat(
    val id: String,
    val peer: Contact,
    val messages: List<Message>
)
