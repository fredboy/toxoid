package ru.fredboy.toxoid.clean.presentation.model

import android.graphics.drawable.Drawable

data class ChatListItemVo(
    val chatId: String,
    val peerName: String,
    val lastMessageText: String,
    val lastMessageDateAndTime: String,
    val contactDrawable: Drawable,
)