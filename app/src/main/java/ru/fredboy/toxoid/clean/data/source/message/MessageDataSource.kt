package ru.fredboy.toxoid.clean.data.source.message

import kotlinx.coroutines.flow.Flow
import ru.fredboy.toxoid.clean.data.model.room.MessageEntity

interface MessageDataSource {

    suspend fun getAll(): List<MessageEntity>

    suspend fun getAllFromChat(chatId: String): List<MessageEntity>

    suspend fun sendMessage(message: MessageEntity)

    suspend fun add(message: MessageEntity)

    fun getNewMessageFlow(): Flow<MessageEntity>

}