package ru.fredboy.toxoid.clean.data.mapper

import ru.fredboy.toxoid.clean.data.model.room.MessageEntity
import ru.fredboy.toxoid.clean.domain.model.Message
import java.util.*
import javax.inject.Inject

class MessageMapper @Inject constructor() {

    fun map(messageEntity: MessageEntity): Message {
        return Message(
            id = messageEntity.id,
            chatId = messageEntity.chatId,
            senderId = messageEntity.senderId,
            text = messageEntity.text,
            date = Date(messageEntity.timestamp)
        )
    }

    fun map(message: Message): MessageEntity {
        return MessageEntity(
            id = message.id,
            chatId = message.chatId,
            senderId = message.senderId,
            text = message.text,
            timestamp = message.date.time
        )
    }

}