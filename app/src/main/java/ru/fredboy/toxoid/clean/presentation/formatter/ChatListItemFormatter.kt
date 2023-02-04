package ru.fredboy.toxoid.clean.presentation.formatter

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.fredboy.toxoid.clean.domain.model.Chat
import ru.fredboy.toxoid.clean.domain.model.Identicon
import ru.fredboy.toxoid.clean.presentation.model.ChatListItemVo
import java.util.*
import javax.inject.Inject

class ChatListItemFormatter @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dateTimeFormatter: DateTimeFormatter
) {

    fun format(chat: Chat, identicon: Identicon): ChatListItemVo {
        val lastMessage = chat.messages.maxByOrNull { it.date }
        return ChatListItemVo(
            chatId = chat.id,
            peerName = chat.peer.name,
            lastMessageText = lastMessage?.text.orEmpty(),
            lastMessageDateAndTime = lastMessage?.date
                ?.let(::formatDateAndTime).orEmpty(),
            contactDrawable = identicon.getDrawable(context.resources)
        )
    }

    private fun formatDateAndTime(date: Date): String {
        val formattedToday = dateTimeFormatter.formatDate(Date())
        val formattedDate = dateTimeFormatter.formatDate(date)

        return if (formattedDate == formattedToday) {
            dateTimeFormatter.formatTime(date)
        } else {
            formattedDate
        }
    }

}