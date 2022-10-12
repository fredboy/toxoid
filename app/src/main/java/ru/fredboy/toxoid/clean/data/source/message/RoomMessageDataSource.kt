package ru.fredboy.toxoid.clean.data.source.message

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.fredboy.toxoid.clean.data.model.room.MessageEntity
import ru.fredboy.toxoid.clean.data.source.room.MainDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomMessageDataSource @Inject constructor(
    private val mainDatabase: MainDatabase
) : MessageDataSource {

    private val newMessageFlow = MutableSharedFlow<MessageEntity>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override suspend fun getAll(): List<MessageEntity> {
        return mainDatabase.messageDao.getAll()
    }

    override suspend fun getAllFromChat(chatId: String): List<MessageEntity> {
        return mainDatabase.messageDao.getAllFromChat(chatId)
    }

    override suspend fun add(message: MessageEntity) {
        mainDatabase.messageDao.insert(message)
    }

    override suspend fun sendMessage(message: MessageEntity) {
        add(message)
        newMessageFlow.emit(message)
    }

    override fun getNewMessageFlow(): Flow<MessageEntity> {
        return newMessageFlow
    }
}