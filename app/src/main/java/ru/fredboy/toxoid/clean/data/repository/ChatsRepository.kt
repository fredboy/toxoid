package ru.fredboy.toxoid.clean.data.repository

import ru.fredboy.toxoid.clean.data.mapper.ChatMapper
import ru.fredboy.toxoid.clean.data.source.chat.ChatDataSource
import ru.fredboy.toxoid.clean.data.source.contact.ContactDataSource
import ru.fredboy.toxoid.clean.data.source.message.MessageDataSource
import ru.fredboy.toxoid.clean.domain.model.Chat
import ru.fredboy.toxoid.clean.domain.model.Contact
import ru.fredboy.toxoid.utils.generateRandomStringId
import ru.fredboy.toxoid.utils.withIoDispatcher
import javax.inject.Inject

class ChatsRepository @Inject constructor(
    private val chatDataSource: ChatDataSource,
    private val messageDataSource: MessageDataSource,
    private val contactDataSource: ContactDataSource,
    private val chatMapper: ChatMapper,
) {

    suspend fun add(chat: Chat) {
        withIoDispatcher {
            val entity = chatMapper.map(chat)
            chatDataSource.add(entity)
        }
    }

    suspend fun getForContact(contactId: String): Chat? {
        return withIoDispatcher {
            val chat = chatDataSource.getForContact(contactId) ?: return@withIoDispatcher null
            val contact = contactDataSource.getById(contactId) ?: return@withIoDispatcher null
            val messages = messageDataSource.getAllFromChat(chat.id)

            chatMapper.map(chat, contact, messages)
        }
    }

    suspend fun getAll(): List<Chat> {
        return withIoDispatcher {
            val chats = chatDataSource.getAll()
            val messages = messageDataSource.getAll()
            val contacts = contactDataSource.getAll()

            chatMapper.map(chats, contacts, messages)
                .sortedByDescending { it.messages.lastOrNull()?.date }
        }
    }

    suspend fun getById(chatId: String): Chat? {
        return withIoDispatcher {
            val chat = chatDataSource.getById(chatId) ?: return@withIoDispatcher null
            val contact = contactDataSource.getById(chat.peerId) ?: return@withIoDispatcher null
            val messages = messageDataSource.getAllFromChat(chat.id)

            chatMapper.map(chat, contact, messages)
        }
    }

    suspend fun createForContact(contact: Contact): Chat {
        return withIoDispatcher {
            val exists = getForContact(contact.id) != null
            if (exists) {
                throw IllegalArgumentException("Chat with contact ${contact.id} already exists")
            }

            val chat = Chat(
                id = generateRandomStringId(),
                peer = contact,
                messages = emptyList()
            )

            add(chat)
            chat
        }
    }

}