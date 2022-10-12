package ru.fredboy.toxoid.clean.data.mapper

import ru.fredboy.toxoid.clean.data.model.room.ChatEntity
import ru.fredboy.toxoid.clean.data.model.room.ContactEntity
import ru.fredboy.toxoid.clean.data.model.room.MessageEntity
import ru.fredboy.toxoid.clean.domain.model.Chat
import javax.inject.Inject

class ChatMapper @Inject constructor(
    private val messageMapper: MessageMapper,
    private val contactMapper: ContactMapper
) {

    fun map(chat: Chat): ChatEntity {
        return ChatEntity(
            id = chat.id,
            peerId = chat.peer.id
        )
    }

    fun map(
        chatEntity: ChatEntity,
        contactEntity: ContactEntity,
        messages: List<MessageEntity>
    ): Chat {
        return Chat(
            id = chatEntity.id,
            peer = contactMapper.map(contactEntity),
            messages = messages.map(messageMapper::map)
        )
    }

    fun map(
        chatEntities: List<ChatEntity>,
        peerEntities: List<ContactEntity>,
        messageEntities: List<MessageEntity>
    ): List<Chat> {
        val messagesMap = messageEntities.groupBy { it.chatId }

        return chatEntities.mapNotNull { chatEntity ->
            val chatId = chatEntity.id
            val peerId = chatEntity.peerId

            val peer = peerEntities.firstOrNull { peer -> peerId == peer.id }
            val messages = messagesMap[chatId]

            if (peer != null && messages != null) {
                map(chatEntity, peer, messages)
            } else {
                null
            }
        }

    }

}