package ru.fredboy.toxoid.clean.data.source.chat

import ru.fredboy.toxoid.clean.data.model.room.ChatEntity

interface ChatDataSource {

    suspend fun getById(chatId: String): ChatEntity?

    suspend fun getAll(): List<ChatEntity>

    suspend fun getAllForUser(userId: String): List<ChatEntity>

    suspend fun getForContact(contactId: String): ChatEntity?

    suspend fun add(chat: ChatEntity)

}