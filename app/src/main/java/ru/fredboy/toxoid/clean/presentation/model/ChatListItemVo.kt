package ru.fredboy.toxoid.clean.presentation.model

data class ChatListItemVo(
    val chatId: String,
    val peerName: String,
    val lastMessageText: String,
    val lastMessageDateAndTime: String
)