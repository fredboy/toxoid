package ru.fredboy.toxoid.clean.presentation.formatter

import ru.fredboy.toxoid.clean.domain.model.Message
import ru.fredboy.toxoid.clean.presentation.model.MessageVo
import javax.inject.Inject

class MessageFormatter @Inject constructor(
    private val dateTimeFormatter: DateTimeFormatter
) {

    fun format(message: Message, isFromLocalPeer: Boolean): MessageVo {
        return MessageVo(
            text = message.text,
            date = "${dateTimeFormatter.formatDate(message.date)} " +
                    "${dateTimeFormatter.formatTime(message.date)} ",
            isFromUser = isFromLocalPeer
        )
    }

}